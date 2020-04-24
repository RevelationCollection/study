package com.study.jdk.net.pipeline;

public class PipelineDemo {

    public HandlerChainContext head = new HandlerChainContext(new AbstractHandler() {
        @Override
        void doHandler(HandlerChainContext handlerChainContext, Object arg) {
            handlerChainContext.runNex(arg);
        }
    });

    public void requestProcess(Object arg){
        this.head.handler(arg);
    }

    public void addLast(AbstractHandler handler){
        HandlerChainContext context = this.head;
        while (context.getNext()!=null) {
            context = context.getNext();
        }
        context.setNext(new HandlerChainContext(handler));
    }

    public static void main(String[] args) {
        PipelineDemo pipeline = new PipelineDemo();
        pipeline.addLast(new SimpleHandle());
        pipeline.addLast(new PlusHandler());
        pipeline.addLast(new PlusHandler());
        pipeline.addLast(new SimpleHandle());
        pipeline.addLast(new TailHandler());
        pipeline.requestProcess("start -->");
    }
}

/**
 * handler 上下文 维护链表和链执行
 */
class HandlerChainContext{
    private  HandlerChainContext next; //下一个节点
    private AbstractHandler handler;
    public HandlerChainContext(AbstractHandler handler){
        this.handler = handler;
    }

    void handler(Object arg){
        this.handler.doHandler(this,arg);
    }

    void runNex(Object arg){
        if (this.next==null)
            return;
        this.next.handler(arg);
    }

    public HandlerChainContext getNext(){
        return this.next;
    }

    public void setNext(HandlerChainContext next){
        this.next = next;
    }
}

abstract class AbstractHandler{

    abstract void doHandler(HandlerChainContext handlerChainContext,Object arg);
}

class PlusHandler extends AbstractHandler{

    @Override
    void doHandler(HandlerChainContext handlerChainContext, Object arg) {
        arg = arg + ".... is plus";
        System.out.println("PlusHandler .处理："+arg);
        //执行下一个
        handlerChainContext.runNex(arg);
    }
}

class SimpleHandle extends AbstractHandler{

    @Override
    void doHandler(HandlerChainContext handlerChainContext, Object arg) {
        arg = arg + ".... is simple";
        System.out.println("SimpleHandle .处理："+arg);
        //执行下一个
        handlerChainContext.runNex(arg);
    }
}

class TailHandler extends AbstractHandler{

    @Override
    void doHandler(HandlerChainContext handlerChainContext, Object arg) {
        arg = arg + ".... is tail";
        System.out.println("TailHandler .处理："+arg);
    }
}