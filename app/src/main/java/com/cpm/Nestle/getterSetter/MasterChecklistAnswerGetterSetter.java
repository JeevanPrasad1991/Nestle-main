
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterChecklistAnswerGetterSetter {
    @SerializedName("Mapping_Visicooler")
    @Expose
    private List<MappingVisicooler> mappingVisicooler = null;

    public List<MappingVisicooler> getMappingVisicooler() {
        return mappingVisicooler;
    }
    @SerializedName("Master_ChecklistAnswer")
    @Expose
    private List<MasterChecklistAnswer> masterChecklistAnswer = null;

    public List<MasterChecklistAnswer> getMasterChecklistAnswer() {
        return masterChecklistAnswer;
    }
    @SerializedName("Mapping_SubProgramChecklist")
    @Expose
    private List<MappingSubProgramChecklist> mappingSubProgramChecklist = null;

    public List<MappingSubProgramChecklist> getMappingSubProgramChecklist() {
        return mappingSubProgramChecklist;
    }

    public List<MappingProgram> getMappingProgramList() {
        return mappingProgramList;
    }

    @SerializedName("Mapping_Program")
    @Expose
    private List<MappingProgram> mappingProgramList = null;

    @SerializedName("Mapping_VisicoolerChecklist")
    @Expose
    private List<MappingVisicoolerChecklist> mappingVisicoolerChecklist = null;

    public List<MappingVisicoolerChecklist> getMappingVisicoolerChecklist() {
        return mappingVisicoolerChecklist;
    }
    @SerializedName("Store_Grading")
    @Expose
    private List<StoreGrading> storeGrading = null;

    public List<StoreGrading> getStoreGrading() {
        return storeGrading;
    }
}
