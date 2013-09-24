package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;
import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RelatedResourceResultUtils.getRelatedResourceResultsFromRows;
import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RepositoryUtils.isLastPublishedVersionConditions;

import java.util.List;

import javax.persistence.Query;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.domain.utils.RepositoryUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for DatasetVersion
 */
@Repository("datasetVersionRepository")
public class DatasetVersionRepositoryImpl extends DatasetVersionRepositoryBase {

    @Autowired
    private LifeCycleStatisticalResourceRepository      lifeCycleStatisticalResourceRepository;

    @Autowired
    private SiemacMetadataStatisticalResourceRepository siemacMetadataStatisticalResourceRepository;

    public DatasetVersionRepositoryImpl() {
    }

    @Override
    public DatasetVersion retrieveByUrn(String urn) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> condition = criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().urn()).eq(urn).distinctRoot().build();

        // Find
        List<DatasetVersion> result = findByCondition(condition);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one dataset with urn " + urn);
        }

        return result.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DatasetVersion retrieveLastVersion(String datasetUrn) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.dataset().identifiableStatisticalResource().urn())
                .eq(datasetUrn).orderBy(CriteriaUtils.getDatetimedLeafProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().creationDate(), DatasetVersion.class)).descending()
                .distinctRoot().build();

        PagingParameter paging = PagingParameter.rowAccess(0, 1);
        // Find
        PagedResult<DatasetVersion> result = findByCondition(conditions, paging);

        // Check for unique result and return. We have at least one datasetVersion
        if (result.getRowCount() == 0) {
            throw new MetamacException(ServiceExceptionType.DATASET_LAST_VERSION_NOT_FOUND, datasetUrn);
        }

        return result.getValues().get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DatasetVersion retrieveLastPublishedVersion(String datasetUrn) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.dataset().identifiableStatisticalResource().urn())
                .eq(datasetUrn).and().withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED).and()
                .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().validFrom()).lessThanOrEqual(new DateTime())
                .orderBy(CriteriaUtils.getDatetimedLeafProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().creationDate(), DatasetVersion.class)).descending().distinctRoot().build();

        PagingParameter paging = PagingParameter.rowAccess(0, 1);
        // Find
        PagedResult<DatasetVersion> result = findByCondition(conditions, paging);

        // Check for unique result and return
        if (result.getRowCount() != 0) {
            return result.getValues().get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean isLastVersion(String datasetVersionUrn) throws MetamacException {
        DatasetVersion datasetVersion = retrieveByUrn(datasetVersionUrn);
        DatasetVersion lastVersion = retrieveLastVersion(datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn());

        return lastVersion.getSiemacMetadataStatisticalResource().getUrn().equals(datasetVersionUrn);
    }

    @Override
    public DatasetVersion retrieveByVersion(Long statisticalResourceId, String versionLogic) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.dataset().id()).eq(statisticalResourceId)
                .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq(versionLogic).distinctRoot().build();

        // Find
        List<DatasetVersion> result = findByCondition(conditions);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, statisticalResourceId, versionLogic);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one dataset version with id " + statisticalResourceId + " and versionLogic " + versionLogic + " found");
        }
        return result.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> retrieveDimensionsIds(DatasetVersion datasetVersion) throws MetamacException {
        //@formatter:off
        Query query = getEntityManager().createQuery(
                "select code.dsdComponentId " +
                "from   CodeDimension code " +
                "where id in (" +
                "   select  max(id) " +
                "   from    CodeDimension c " +
                "   where c.dsdComponentId = code.dsdComponentId " +
                "       and datasetVersion = :datasetVersion) " +
                "   order by code.id");
        //@formatter:on
        query.setParameter("datasetVersion", datasetVersion);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RelatedResourceResult> retrieveIsRequiredByOnlyLastPublished(DatasetVersion datasetVersion) throws MetamacException {
        //@formatter:off
        String queryLinkedDirectlyToDatasetVersion = 
            "         (query.Dataset_Version_Fk = :datasetVersionFk) ";

        String queryLinkedToDatasetAndDatasetVersionLastPublishedVersion = 
            "         (query.dataset_fk = dataset_version.dataset_fk" + 
            "           And " + RepositoryUtils.buildLastPublishedVersionCondition("stat_dataset") + ") ";
        
        Query query = getEntityManager().createNativeQuery(
                "select stat.code,"+
                "       stat.urn, " +
                "       operItems.code as statOperCode, " +
                "       operItems.urn as statOperUrn, " +
                "       maintainerItems.code_nested, " +
                "       stat.version_logic, " +
                "       loc.locale, " +
                "       loc.label " +
                " from  Tb_External_Items operItems, " +
                "       Tb_External_Items maintainerItems, " +
                "       Tb_Queries_Versions query Inner Join Tb_Stat_Resources stat On query.lifecycle_resource_fk = stat.Id, " +
                "       Tb_localised_strings loc, "+
                "       Tb_datasets_versions dataset_version inner join tb_stat_resources stat_dataset "+
                "           on dataset_version.siemac_resource_fk = stat_dataset.id " +
                " Where stat.Stat_Operation_Fk = operItems.Id " +
                "   And stat.Maintainer_Fk = maintainerItems.Id "+
                "   And stat.Title_Fk = loc.International_String_Fk " +
                "   And dataset_version.id = :datasetVersionFk " +
                "   And ( " +
                "           "+queryLinkedDirectlyToDatasetVersion+" "+
                "       OR "+
                "           "+queryLinkedToDatasetAndDatasetVersionLastPublishedVersion+" "+
                "       ) " +
                "   And  " + isLastPublishedVersionConditions);

        //     @formatter:on
        query.setParameter("publishedProcStatus", ProcStatusEnum.PUBLISHED.name());
        query.setParameter("datasetVersionFk", datasetVersion.getId());
        query.setParameter("now", new DateTime().toDate());

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, TypeRelatedResourceEnum.QUERY_VERSION);
        return resources;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RelatedResourceResult> retrieveIsRequiredBy(DatasetVersion datasetVersion) throws MetamacException {
        //     @formatter:off
        String queryLinkedDirectlyToDatasetVersion =              
            "         (query.Dataset_Version_Fk = :datasetVersionFk) ";
        
        String queryLinkedToDatasetAndDatasetVersionIsLastVersion = 
            "         (query.dataset_fk = dataset_version.dataset_fk" +
            "           And stat_dataset.Last_Version = 1) ";
        
        String queryLinkedToDatasetAndDatasetVersionLastPublishedVersion = 
            "         (query.dataset_fk = dataset_version.dataset_fk" +
            "           And "+RepositoryUtils.buildLastPublishedVersionCondition("stat_dataset")+ ") ";
        
        Query query = getEntityManager().createNativeQuery(
                "select stat.code, " +
                "       stat.urn, " +
                "       operItems.code as statOperCode, " +
                "       operItems.urn as statOperUrn, " +
                "       maintainerItems.code_nested, " +
                "       stat.version_logic, " +
                "       loc.locale, " +
                "       loc.label  " +
                "from   Tb_External_Items operItems, " +
                "       Tb_External_Items maintainerItems, " +
                "       Tb_Queries_Versions query Inner Join Tb_Stat_Resources stat " +
                "           On query.lifecycle_resource_fk = stat.Id, " + 
                "       Tb_localised_strings loc, " +
                "       Tb_datasets_versions dataset_version inner join tb_stat_resources stat_dataset "+
                "           on dataset_version.siemac_resource_fk = stat_dataset.id " +
        		"Where stat.Stat_Operation_Fk = operItems.Id " +
        		"     And stat.Maintainer_Fk = maintainerItems.Id " +
        		"     And stat.Title_Fk = loc.International_String_Fk " +
        		"     And dataset_version.id = :datasetVersionFk " +
        		"     And ( "+
        		            queryLinkedDirectlyToDatasetVersion +
        		"         OR "+
        		            queryLinkedToDatasetAndDatasetVersionIsLastVersion +
	            "         OR "+
        		            queryLinkedToDatasetAndDatasetVersionLastPublishedVersion +
	            "          ) ");

        //     @formatter:on
        query.setParameter("publishedProcStatus", ProcStatusEnum.PUBLISHED.name());
        query.setParameter("now", new DateTime().toDate());
        query.setParameter("datasetVersionFk", datasetVersion.getId());

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, TypeRelatedResourceEnum.QUERY_VERSION);
        return resources;
    }
    @Override
    public RelatedResourceResult retrieveIsReplacedByVersionOnlyLastPublished(DatasetVersion datasetVersion) throws MetamacException {
        return lifeCycleStatisticalResourceRepository.retrieveIsReplacedByVersionOnlyLastPublished(datasetVersion.getId(), TypeRelatedResourceEnum.DATASET_VERSION);
    }

    @Override
    public RelatedResourceResult retrieveIsReplacedByVersion(DatasetVersion datasetVersion) throws MetamacException {
        return lifeCycleStatisticalResourceRepository.retrieveIsReplacedByVersion(datasetVersion.getId(), TypeRelatedResourceEnum.DATASET_VERSION);
    }

    @Override
    public RelatedResourceResult retrieveIsReplacedBy(DatasetVersion datasetVersion) throws MetamacException {
        return siemacMetadataStatisticalResourceRepository.retrieveIsReplacedBy(datasetVersion.getId(), TypeRelatedResourceEnum.DATASET_VERSION);
    }

    @Override
    public RelatedResourceResult retrieveIsReplacedByOnlyLastPublished(DatasetVersion datasetVersion) throws MetamacException {
        return siemacMetadataStatisticalResourceRepository.retrieveIsReplacedByOnlyLastPublished(datasetVersion.getId(), TypeRelatedResourceEnum.DATASET_VERSION);
    }

    @Override
    public List<RelatedResourceResult> retrieveIsPartOf(DatasetVersion datasetVersion) throws MetamacException {
        //     @formatter:off
        Query query = getEntityManager().createNativeQuery(
            "SELECT  distinct stat.code, " +
            "        stat.urn, " +
            "        operation.code AS operCode, " +
            "        operation.urn AS operUrn, " +
            "        maintainer.code_nested AS code_nested, " +
            "        stat.version_logic, "+
            "        loc.locale, " +
            "        loc.label " +
            "FROM    tb_elements_levels elem INNER JOIN tb_cubes cubes " + 
            "            on cubes.ID = elem.table_fk, " +
            "        tb_publications_versions pub INNER JOIN tb_stat_resources stat " +
            "            ON pub.siemac_resource_fk = stat.ID, " +
            "        tb_related_resources related, " +
            "        tb_external_items operation, " +
            "        tb_external_items maintainer, " +
            "        tb_datasets dataset, " +
            "        tb_datasets_versions dataset_version INNER JOIN tb_stat_resources stat_dataset "+
            "            ON dataset_version.siemac_resource_fk = stat_dataset.ID, "+
            "        tb_localised_strings loc "+
            "WHERE       cubes.dataset_fk = dataset.ID "+
            "    AND     dataset_version.dataset_fk = dataset.ID " +
            "    AND     dataset_version.ID = :datasetVersionFk " +
            "    AND     stat_dataset.last_version = 1 " +
            "    AND     elem.publication_version_all_fk = pub.ID " +
            "    AND     stat.title_fk = loc.international_string_fk " +
            "    AND     (stat.last_version = 1 " +
            "           OR "+isLastPublishedVersionConditions+") " +    
            "    AND     operation.ID = stat.stat_operation_fk " +
            "    AND     maintainer.id = stat.maintainer_fk");

        //     @formatter:on
        query.setParameter("datasetVersionFk", datasetVersion.getId());
        query.setParameter("publishedProcStatus", ProcStatusEnum.PUBLISHED.name());
        query.setParameter("now", new DateTime().toDate());

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, TypeRelatedResourceEnum.PUBLICATION_VERSION);
        return resources;
    }

    @Override
    public List<RelatedResourceResult> retrieveIsPartOfOnlyLastPublished(DatasetVersion datasetVersion) throws MetamacException {
        //     @formatter:off
        Query query = getEntityManager().createNativeQuery(
                "SELECT     distinct stat.code, " +
                "           stat.urn, " +
                "           operation.code AS operCode, " +
                "           operation.urn AS operUrn,  " +
                "           maintainer.code_nested AS code_nested,  " +
                "           stat.version_logic, " +
                "           loc.locale, " +
                "           loc.label, stat_dataset.valid_to, pub.id " +
                "FROM       tb_elements_levels elem INNER JOIN tb_cubes cubes " +
                "              on cubes.ID = elem.table_fk,  " +
                "           tb_publications_versions pub INNER JOIN tb_stat_resources stat " +
                "               ON pub.siemac_resource_fk = stat.ID, " +
                "           tb_external_items operation, " +
                "           tb_external_items maintainer, " +
                "           tb_datasets dataset, " +
                "           tb_datasets_versions dataset_version INNER JOIN tb_stat_resources stat_dataset " + 
                "               ON dataset_version.siemac_resource_fk = stat_dataset.ID, " +
                "           tb_localised_strings loc  " +
                "WHERE      cubes.dataset_fk = dataset.ID " + 
                "   AND     dataset_version.dataset_fk = dataset.ID " + 
                "   AND     dataset_version.ID = :datasetVersionFk " +
                "   AND     stat_dataset.proc_status = :publishedProcStatus " +
                "   AND     stat_dataset.valid_from <= :now " +
                "   AND     (stat_dataset.valid_to > :now or stat_dataset.valid_to is null) " +
                "   AND     elem.publication_version_all_fk = pub.ID  "  +
                "   AND     stat.title_fk = loc.international_string_fk  " +
                "   AND     stat.proc_status = :publishedProcStatus " +
                "   AND     "+ isLastPublishedVersionConditions +
                "   AND     operation.ID = stat.stat_operation_fk " +
                "   AND     maintainer.id = stat.maintainer_fk ");
        
        //     @formatter:on
        query.setParameter("datasetVersionFk", datasetVersion.getId());
        query.setParameter("publishedProcStatus", ProcStatusEnum.PUBLISHED.name());
        query.setParameter("now", new DateTime().toDate());

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, TypeRelatedResourceEnum.PUBLICATION_VERSION);
        return resources;
    }
}
