public class Record {
    public String recordId;
    public String name;

    public Record(){
        this.recordId = "";
        this.name = "";
    }
    public Record(String recordId, String name){
        this.recordId = recordId;
        this.name = name;
    }
    public String getRecordId() {
        return recordId;
    }

    public String getName() {
        return name;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
