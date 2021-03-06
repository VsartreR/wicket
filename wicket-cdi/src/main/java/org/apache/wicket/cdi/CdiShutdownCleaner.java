/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.cdi;

import javax.enterprise.inject.spi.BeanManager;

import org.apache.wicket.Application;
import org.apache.wicket.IApplicationListener;
import org.apache.wicket.util.lang.Args;

/**
 * Listens to application shutdown and cleans up
 * 
 * @author igor
 */
class CdiShutdownCleaner implements IApplicationListener
{
	private final BeanManager beanManager;
	private final boolean preDestroyApplication;

	public CdiShutdownCleaner(BeanManager beanManager, boolean preDestroyApplication)
	{
		Args.notNull(beanManager, "beanManager");

		this.beanManager = beanManager;
		this.preDestroyApplication = preDestroyApplication;
	}

	@Override
	public void onAfterInitialized(Application application)
	{
		// noop
	}

	@Override
	public void onBeforeDestroyed(Application application)
	{
		if (preDestroyApplication)
		{
			NonContextual.of(application, beanManager).preDestroy(application);
		}
		NonContextual.undeploy(beanManager);
	}

}
