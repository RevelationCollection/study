package lock;

public class WySemaphore {

    public WySemaphore(int count){
        wyAqs.getState().set(count);
    }

    WyAqs wyAqs = new WyAqs(){
        @Override
        public int tryAcquireShare() {
            for (; ;) {
                int count = getState().get();
                int n = count -1;
                if(count<=0 || n<0){
                    return -1;
                }
                if(getState().compareAndSet(count,n)){
                    return 1;
                }
            }
        }

        @Override
        public boolean tryReleaseShare() {
            return getState().incrementAndGet()>=0;
        }
    };

    public void acquire(){
        wyAqs.acquireShare();
    }

    public void release(){
        wyAqs.releaseShare();
    }
}
