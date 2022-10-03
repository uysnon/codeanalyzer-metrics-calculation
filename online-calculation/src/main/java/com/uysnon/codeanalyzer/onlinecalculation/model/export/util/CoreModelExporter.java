package com.uysnon.codeanalyzer.onlinecalculation.model.export.util;

import com.uysnon.codeanalyzer.core.syntaxelements.ProjectTree;
import com.uysnon.codeanalyzer.core.syntaxelements.SourceCodeUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnit;
import com.uysnon.codeanalyzer.onlinecalculation.model.export.ExportUnitTypes;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CoreModelExporter {

    public List<ExportUnit> mapFromProjectTree(ProjectTree projectTree) {
        List<ExportUnit> result = new ArrayList<>();
        for (SourceCodeUnit sourceCodeUnit : projectTree.getSourceCodeUnits()) {
            result.add(ExportUnitTypes.export(sourceCodeUnit));
        }
        return result;
    }

}
