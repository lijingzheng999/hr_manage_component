package hr.manage.component.util;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	//根据环境加载相应的配置  测试：test  正式： online
	private static final String ENV = System.getProperty("ENV");
	private static final String PREFIX_FILE_NAME = (ENV == null ? "online" : ENV) ;
    private static String dataSourceRef = "dataSource-online"; // 数据源配置
    static {
        try {
            
            if (PREFIX_FILE_NAME.toLowerCase().startsWith("online")) {
                // 内网正式库
                dataSourceRef = "dataSource-online";
            } else if (PREFIX_FILE_NAME.toLowerCase().startsWith("test")) {
                // 外网正式库
                dataSourceRef = "dataSource-test";
            } else{
            	// 测试库
                dataSourceRef = "dataSource-dev";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceRef;
    }

}