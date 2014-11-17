package org.siemac.metamac.statistical.resources.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.SchedulerRepository;
import org.siemac.metamac.common.test.MetamacBaseTest;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests.DataBaseProvider;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockAnnotationRule;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;
import org.siemac.metamac.statistical.resources.core.task.serviceimpl.TaskServiceImpl;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CategorisationMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DimensionRepresentationMappingMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public abstract class StatisticalResourcesBaseTest extends MetamacBaseTest {

    protected static String                                 EMPTY                                     = StringUtils.EMPTY;

    protected static Long                                   ID_NOT_EXISTS                             = Long.valueOf(-1);
    protected static String                                 URN_NOT_EXISTS                            = "not_exists";
    protected static String                                 CODE_NOT_EXISTS                           = "NOT_EXISTS";
    public static final String                              INIT_VERSION                              = "001.000";

    protected QueryMockFactory                              queryMockFactory                          = QueryMockFactory.getInstance();
    protected QueryVersionMockFactory                       queryVersionMockFactory                   = QueryVersionMockFactory.getInstance();

    protected PublicationMockFactory                        publicationMockFactory                    = PublicationMockFactory.getInstance();
    protected PublicationVersionMockFactory                 publicationVersionMockFactory             = PublicationVersionMockFactory.getInstance();
    protected ChapterMockFactory                            chapterMockFactory                        = ChapterMockFactory.getInstance();
    protected CubeMockFactory                               cubeMockFactory                           = CubeMockFactory.getInstance();

    protected DatasetMockFactory                            datasetMockFactory                        = DatasetMockFactory.getInstance();
    protected DatasetVersionMockFactory                     datasetVersionMockFactory                 = DatasetVersionMockFactory.getInstance();
    protected DatasourceMockFactory                         datasourceMockFactory                     = DatasourceMockFactory.getInstance();
    protected CategorisationMockFactory                     categorisationMockFactory                 = CategorisationMockFactory.getInstance();
    protected DimensionRepresentationMappingMockFactory     dimensionRepresentationMappingMockFactory = DimensionRepresentationMappingMockFactory.getInstance();

    protected final StatisticalResourcesNotPersistedDoMocks notPersistedDoMocks                       = StatisticalResourcesNotPersistedDoMocks.getInstance();
    protected StatisticalResourcesPersistedDoMocks          persistedDoMocks                          = StatisticalResourcesPersistedDoMocks.getInstance();

    protected StatisticOfficialityMockFactory               statisticOfficialityMockFactory           = StatisticOfficialityMockFactory.getInstance();

    @Value("${metamac.statistical_resources.db.provider}")
    private String                                          databaseProvider;

    @Rule
    public MockAnnotationRule                               mockRule                                  = new MockAnnotationRule();

    @Rule
    public ExpectedException                                thrown                                    = ExpectedException.none();

    protected JdbcTemplate                                  jdbcTemplateRepository;
    protected JdbcTemplate                                  jdbcTemplateResources;

    protected ServiceContext getServiceContextWithoutPrincipal() {
        return mockServiceContextWithoutPrincipal();
    }

    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.ADMINISTRADOR);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoProduccion() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION);
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, StatisticalResourcesRoleEnum role) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), StatisticalResourcesConstants.APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    @Override
    protected DataBaseProvider getDatabaseProvider() {
        return DataBaseProvider.valueOf(databaseProvider);
    }

    protected void waitUntilJobFinished(boolean initializeWait) throws InterruptedException, SchedulerException {
        Scheduler sched = SchedulerRepository.getInstance().lookup(TaskServiceImpl.SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler

        // Wait until the job is scheduled
        while (initializeWait) {
            if (sched.getCurrentlyExecutingJobs().size() != 0) {
                break;
            } else {
                Thread.sleep(1 * 1000);
            }
        }

        // Wait until the job is finished
        while (sched.getCurrentlyExecutingJobs().size() != 0) {
            Thread.sleep(1 * 1000);
        }
    }

    protected String buildCommaSeparatedString(String... items) {
        return StringUtils.join(items, ", ");
    }

    protected String getResourceUrn(HasLifecycle hasLifecycle) {
        return hasLifecycle.getLifeCycleStatisticalResource().getUrn();
    }

    protected String buildDatasetUrn(String maintainerCode, String operationCode, int datasetSequentialId, String versionNumber) {
        StringBuilder strBuilder = new StringBuilder("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=");
        strBuilder.append(maintainerCode).append(":").append(operationCode).append("_").append(String.format("%06d", datasetSequentialId)).append("(").append(versionNumber).append(")");
        return strBuilder.toString();
    }

    protected class TableNameResultSetExtractor implements ResultSetExtractor<String> {

        @Override
        public String extractData(ResultSet rs) throws SQLException {
            return rs.getString(1);
        }
    }

    protected class TableNameRowMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int line) throws SQLException {
            TableNameResultSetExtractor extractor = new TableNameResultSetExtractor();
            return extractor.extractData(rs);
        }
    }

    protected void clearDataBase() {
        // REPOSITORY
        {
            List<String> tableNames = this.jdbcTemplateRepository.query("select TABLE_NAME from TB_DATASETS", new TableNameRowMapper());

            // Truncate tables
            this.jdbcTemplateRepository.update("truncate table TB_LOCALISED_STRINGS");
            this.jdbcTemplateRepository.update("truncate table TB_DATASET_DIMENSIONS");
            this.jdbcTemplateRepository.update("truncate table TB_ATTRIBUTE_DIMENSIONS");

            // Deletes
            this.jdbcTemplateRepository.update("delete from TB_ATTRIBUTES");
            this.jdbcTemplateRepository.update("delete from TB_DATASETS");
            this.jdbcTemplateRepository.update("delete from TB_INTERNATIONAL_STRINGS");

            // Drop table data
            for (String tableName : tableNames) {
                this.jdbcTemplateRepository.update("drop table " + tableName);
            }
        }

        // RESOURCES
        {
            this.jdbcTemplateResources.update("truncate table TB_TASKS");
        }

    }
}
