package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.siemac.metamac.core.common.io.FileUtils;
import org.springframework.stereotype.Component;

import com.arte.statistic.parser.csv.CsvParser;
import com.arte.statistic.parser.csv.constants.CsvConstants;
import com.arte.statistic.parser.generic.Reader;

@Component(ManipulateCsvDataService.BEAN_ID)
public class ManipulateCsvDataServiceImpl extends ManipulateDataServiceImplBase<File> implements ManipulateCsvDataService {

    private InputStream is = null;

    @Override
    protected Reader getReader(File source) throws Exception {
        // Parse Csv
        String charsetName = FileUtils.guessCharset(source);
        is = new FileInputStream(source);

        return CsvParser.parseCsv(is, charsetName, CsvConstants.SEPARATOR_TAB);
    }

    @Override
    protected void closeReader() throws Exception {
        IOUtils.closeQuietly(is);
    }

}
