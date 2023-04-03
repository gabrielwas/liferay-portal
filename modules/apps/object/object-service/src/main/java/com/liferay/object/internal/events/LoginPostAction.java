package com.liferay.object.internal.events;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.util.Portal;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gabriel Albuquerque
 */
@Component(property = "key=login.events.post", service = LifecycleAction.class)
public class LoginPostAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_portal.getUserId(httpServletRequest);

	}


	@Reference
	private Portal _portal;

	@Reference
	private ObjectActionEngine _objectActionEngine;
}
