package org.siemac.metamac.statistical.resources.core.query.utils;

import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copyLifeCycleStatisticalResource;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public class QueryVersioningCopyUtils {

    /**
     * Create a new {@link QueryVersion} copying values from a source.
     */
    public static QueryVersion copyQueryVersion(QueryVersion source) {
        QueryVersion target = new QueryVersion();
        target.setLifeCycleStatisticalResource(new LifeCycleStatisticalResource());
        copyQueryVersion(source, target);
        return target;
    }

    /**
     * Copy values from a {@link QueryVersion}
     */
    public static void copyQueryVersion(QueryVersion source, QueryVersion target) {
        target.setLifeCycleStatisticalResource(copyLifeCycleStatisticalResource(source.getLifeCycleStatisticalResource(), target.getLifeCycleStatisticalResource()));

        target.setFixedDatasetVersion(source.getFixedDatasetVersion());
        target.setDataset(source.getDataset());

        target.setQuery(source.getQuery());

        target.setStatus(source.getStatus());
        target.setType(source.getType());

        target.setLatestDataNumber(source.getLatestDataNumber());

        target.getSelection().clear();
        target.getSelection().addAll(copyListQuerySelectionItem(source.getSelection(), target));
    }

    private static List<QuerySelectionItem> copyListQuerySelectionItem(List<QuerySelectionItem> source, QueryVersion targetQueryVersion) {
        if (source.isEmpty()) {
            return new ArrayList<QuerySelectionItem>();
        }

        List<QuerySelectionItem> target = new ArrayList<QuerySelectionItem>();
        for (QuerySelectionItem item : source) {
            target.add(copyQuerySelectionItem(item, targetQueryVersion));
        }
        return target;
    }

    private static QuerySelectionItem copyQuerySelectionItem(QuerySelectionItem source, QueryVersion targetQueryVersion) {
        if (source == null) {
            return null;
        }
        QuerySelectionItem target = new QuerySelectionItem();

        target.setDimension(source.getDimension());
        target.setQueryVersion(targetQueryVersion);

        target.getCodes().clear();
        target.getCodes().addAll(copyListCodeItem(source.getCodes(), target));

        return target;
    }

    private static List<CodeItem> copyListCodeItem(List<CodeItem> source, QuerySelectionItem targetQuerySelectionItem) {
        if (source.isEmpty()) {
            return new ArrayList<CodeItem>();
        }

        List<CodeItem> target = new ArrayList<CodeItem>();
        for (CodeItem item : source) {
            target.add(copyCodeItem(item, targetQuerySelectionItem));
        }
        return target;
    }

    private static CodeItem copyCodeItem(CodeItem source, QuerySelectionItem targetQuerySelectionItem) {
        if (source == null) {
            return null;
        }

        CodeItem target = new CodeItem();
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setQuerySelectionItem(targetQuerySelectionItem);

        return target;
    }
}