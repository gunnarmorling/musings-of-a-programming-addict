/**
 *  Copyright 2010 Gunnar Morling (http://www.gunnarmorling.de/)
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

package de.gmorling.moapa.self_validating;

/**
 * By implementing this interface classes signal that they are validatable by the
 * means of the {@link SelfValidating} constraint annotation.
 * 
 * @author Gunnar Morling
 *
 */
public interface Validatable {

	/**
	 * Will be called by the BV runtime in order to check, whether this object is
	 * valid or not.
	 * 
	 * @return Implementations must return true if this object is valid, false otherwise.
	 */
	public boolean isValid();
}
