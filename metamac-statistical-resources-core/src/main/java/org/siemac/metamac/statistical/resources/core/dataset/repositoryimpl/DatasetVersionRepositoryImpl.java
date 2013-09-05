package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for DatasetVersion
 */
@Repository("datasetVersionRepository")
public class DatasetVersionRepositoryImpl extends DatasetVersionRepositoryBase {

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
    public List<RelatedResourceResult> retrieveLastPublishedVersionResourcesThatRequiresDatasetVersion(DatasetVersion datasetVersion) throws MetamacException {
        //@formatter:off
        String isLastPublishedVersion = "( " +
        		"lifecycle.proc_status = :publishedProcStatus " +
    		    "and lifecycle.Valid_From <= :now and " +
        		    "( lifecycle.Valid_To > :now or lifecycle.Valid_To is null)" +
        		")";
        
        Query query = getEntityManager().createNativeQuery(
                "select lifecycle.code,"+
                "       lifecycle.urn, " +
                "       operItems.code as statOperCode, " +
                "       operItems.urn as statOperUrn, " +
                "       maintainerItems.code_nested, " +
                "       lifecycle.version_logic, " +
                "       loc.locale, " +
                "       loc.label " +
                " from  Tb_External_Items operItems, " +
                "       Tb_External_Items maintainerItems, " +
                "       Tb_Queries_Versions query Inner Join Tb_Stat_Resources lifecycle On query.lifecycle_resource_fk = lifecycle.Id, " +
                "       Tb_localised_strings loc "+
                " Where lifecycle.proc_status = :publishedProcStatus " +
                "   And lifecycle.Stat_Operation_Fk = operItems.Id " +
                "   And lifecycle.Maintainer_Fk = maintainerItems.Id "+
                "   And lifecycle.Title_Fk = loc.International_String_Fk " +
                "   And query.Dataset_Version_Fk = :datasetVersionFk " +
                "   And  " + isLastPublishedVersion);

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
    public List<RelatedResourceResult> retrieveLastVersionResourcesThatRequiresDatasetVersion(DatasetVersion datasetVersion) throws MetamacException {
        String isLastPublishedVersion = "( " + "lifecycle.proc_status = :publishedProcStatus " + "and " + "lifecycle.Valid_From <= :now " + "and "
                + "( lifecycle.Valid_To > :now or lifecycle.Valid_To is null)" + ")";

        //     @formatter:off
        Query query = getEntityManager().createNativeQuery(
                "select lifecycle.code, " +
                "       lifecycle.urn, " +
                "       operItems.code as statOperCode, " +
                "       operItems.urn as statOperUrn, " +
                "       maintainerItems.code_nested, " +
                "       lifecycle.version_logic, " +
                "       loc.locale, " +
                "       loc.label  " +
                "from   Tb_External_Items operItems, " +
                "       Tb_External_Items maintainerItems, " +
                "       Tb_Queries_Versions query Inner Join Tb_Stat_Resources lifecycle " +
                "           On query.lifecycle_resource_fk = lifecycle.Id, " + " Tb_localised_strings loc " +
        		"Where lifecycle.Stat_Operation_Fk = operItems.Id " +
        		"     And lifecycle.Maintainer_Fk = maintainerItems.Id " +
        		"     And lifecycle.Title_Fk = loc.International_String_Fk " +
        		"     And query.Dataset_Version_Fk = :datasetVersionFk  " +
        		"     And ( " +
        		"         lifecycle.Last_Version = 1 " +
        		"         OR" +
        		"       "+isLastPublishedVersion + ")");

        //     @formatter:on
        query.setParameter("publishedProcStatus", ProcStatusEnum.PUBLISHED.name());
        query.setParameter("datasetVersionFk", datasetVersion.getId());
        query.setParameter("now", new DateTime().toDate());

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, TypeRelatedResourceEnum.QUERY_VERSION);
        return resources;
    }

    private List<RelatedResourceResult> getRelatedResourceResultsFromRows(List<Object> rows, TypeRelatedResourceEnum type) {
        Map<String, RelatedResourceResult> resourcesByUrn = new HashMap<String, RelatedResourceResult>();
        for (Object row : rows) {
            Object[] cols = (Object[]) row;
            String queryUrn = (String) cols[1];
            RelatedResourceResult resource = resourcesByUrn.get(queryUrn);
            if (resource == null) {
                resource = new RelatedResourceResult();
                resourcesByUrn.put(queryUrn, resource);
            }
            populateResultWithRow(resource, cols, type);
        }
        List<RelatedResourceResult> resources = new ArrayList<RelatedResourceResult>(resourcesByUrn.values());
        return resources;
    }

    private void populateResultWithRow(RelatedResourceResult resource, Object[] cols, TypeRelatedResourceEnum type) {
        resource.setCode((String) cols[0]);
        resource.setUrn((String) cols[1]);
        resource.setStatisticalOperationCode((String) cols[2]);
        resource.setStatisticalOperationUrn((String) cols[3]);
        resource.setMaintainerNestedCode((String) cols[4]);
        resource.setVersion((String) cols[5]);
        if (resource.getTitle() == null) {
            resource.setTitle(new HashMap<String, String>());
        }
        resource.getTitle().put((String) cols[6], (String) cols[7]);
        resource.setType(type);
    }

}
