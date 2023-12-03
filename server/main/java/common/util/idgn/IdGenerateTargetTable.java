package common.util.idgn;

/**
 * ID 생성을 위한 Table 정보를 담는 객체 입니다.
 *
 * @author 서영석
 * @since 2023.02.27
 */
public class IdGenerateTargetTable {
    //table명
    private String tableName;

    //ID 생성시 앞에 붙일 Text
    private String prefix;

    //prefix를 제외한 ID 생성 길이
    private short length;

    //현재 index
    private int currentIndex;

    //현재 ID
    private String currentId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }
}
