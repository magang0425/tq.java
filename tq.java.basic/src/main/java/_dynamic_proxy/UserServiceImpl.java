package _dynamic_proxy;

public class UserServiceImpl implements UserService{

	@Override
	public void add() {
		System.err.println("add");
	}

	@Override
	public void delete() {
        System.err.println("delete");
	}
}
