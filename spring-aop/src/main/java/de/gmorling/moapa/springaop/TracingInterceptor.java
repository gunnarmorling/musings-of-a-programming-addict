/**
 *  Copyright 2012 Gunnar Morling (http://www.gunnarmorling.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package de.gmorling.moapa.springaop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TracingInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Logger logger = LoggerFactory.getLogger(invocation.getMethod().getDeclaringClass());

		logger.info(">>> {}", new MethodToString(invocation.getMethod()));

		try {
			return invocation.proceed();
		} finally {
			logger.info("<<< {}", new MethodToString(invocation.getMethod()));
		}
	}

	private static class MethodToString {

		private final Method method;

		private MethodToString(Method method) {
			this.method = method;
		}

		@Override
		public String toString() {

			StringBuilder parameterString = new StringBuilder();

			for (Class<?> oneParameterType : method.getParameterTypes()) {
				if (parameterString.length() > 0) {
					parameterString.append(", ");
				}
				parameterString.append(oneParameterType.getName());
			}

			return method.getName() + "(" + parameterString + ")";
		}
	}
}
