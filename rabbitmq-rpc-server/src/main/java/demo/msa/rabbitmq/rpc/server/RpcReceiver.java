package demo.msa.rabbitmq.rpc.server;

/**
 * @author unisk1123
 * @Description
 * @create 2019/4/26
 */
public interface RpcReceiver<I, O> {

    O receive(I message);

}
