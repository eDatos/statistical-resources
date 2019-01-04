package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import org.springframework.stereotype.Component;

import com.arte.statistic.parser.db.DbParser;
import com.arte.statistic.parser.generic.Reader;

@Component(ManipulateDbDataService.BEAN_ID)
public class ManipulateDbDataServiceImpl extends ManipulateDataServiceImplBase<String> implements ManipulateDbDataService {

    @Override
    protected Reader getReader(String source) throws Exception {
        return DbParser.parseTable(source);
    }

    @Override
    protected void closeReader() throws Exception {
        // TODO METAMAC-2866 it's necessary to define the functionality

    }

}
