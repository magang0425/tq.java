package _3_aop._9_spring_aop_process_one._ProxyFactory;

/**
 * @author 734070824@qq.com
 * @date 2018/10/11 19:41
 */
public class MockTask implements ITask{
    @Override
    public void execute() {
        System.err.println("task executor");
    }
}
