package _flink._study._zhisheng_blog._1_data_source;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author 734070824@qq.com
 * @date 2019/3/26 17:44
 */
public class Main2 {


    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new SourceFromMySQL()).print();

        env.execute("Flink add data sourc");
    }
}
