package com.github.wp17.lina.net.future;

import java.util.EventListener;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.mina.core.future.WriteFuture;

import com.github.wp17.lina.common.net.LogicFuture;

public class MinaWriteFuture implements LogicFuture<Object>{
	private WriteFuture writeFuture;
	
	public MinaWriteFuture(WriteFuture writeFuture){
		this.writeFuture = writeFuture;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		throw new UnsupportedOperationException("不可取消");
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return writeFuture.isDone();
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		throw new UnsupportedOperationException("不可获得");
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		throw new UnsupportedOperationException("不可获得");
	}

	@Override
	public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
		return writeFuture.awaitUninterruptibly(timeout, unit);
	}

	@Override
	public boolean awaitUninterruptibly(long timeoutMillis) {
		return writeFuture.awaitUninterruptibly(timeoutMillis);
	}

	@Override
	public void addListener(EventListener listener) {
		
	}

	@Override
	public void removeListener(EventListener listener) {

	}

}
