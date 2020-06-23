package com.liferay.data.engine.internal.nativeobject.tracker;

import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.executor.PortalExecutorConfig;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component(immediate = true, service = DataEnginePortalExecutor.class)
public class DataEnginePortalExecutor {

	public <T extends Throwable> void execute(
		UnsafeRunnable<T> unsafeRunnable) {

		if (PortalRunMode.isTestMode()) {
			try {
				unsafeRunnable.run();
			}
			catch (Throwable t) {
				_log.error(t, t);
			}
		}
		else {
			_noticeableExecutorService.submit(
				() -> {
					try {
						unsafeRunnable.run();
					}
					catch (Throwable t) {
						_log.error(t, t);
					}
				});
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_registerPortalExecutorConfig(bundleContext);

		_noticeableExecutorService = _portalExecutorManager.getPortalExecutor(
			DataEnginePortalExecutor.class.getName());
	}

	@Deactivate
	public void deactivate() {
		_noticeableExecutorService.shutdown();

		_serviceRegistration.unregister();
	}

	private void _registerPortalExecutorConfig(BundleContext bundleContext) {
		PortalExecutorConfig portalExecutorConfig = new PortalExecutorConfig(
			DataEnginePortalExecutor.class.getName(), 1, 1, 60,
			TimeUnit.SECONDS, Integer.MAX_VALUE,
			new NamedThreadFactory(
				DataEnginePortalExecutor.class.getName(),
				Thread.NORM_PRIORITY, PortalClassLoaderUtil.getClassLoader()),
			new ThreadPoolExecutor.AbortPolicy(),
			new ThreadPoolHandlerAdapter() {

				@Override
				public void afterExecute(
					Runnable runnable, Throwable throwable) {

					CentralizedThreadLocal.clearShortLivedThreadLocals();
				}

			});

		_serviceRegistration = bundleContext.registerService(
			PortalExecutorConfig.class, portalExecutorConfig, null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataEnginePortalExecutor.class);

	private NoticeableExecutorService _noticeableExecutorService;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	private ServiceRegistration<PortalExecutorConfig> _serviceRegistration;
}