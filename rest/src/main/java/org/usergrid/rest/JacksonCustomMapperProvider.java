/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.usergrid.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.json.JsonMapperConfigurator;;

@Provider
@Component
@Scope("singleton")
@Produces({ MediaType.APPLICATION_JSON })
public class JacksonCustomMapperProvider implements
		ContextResolver<ObjectMapper> {

	private static final Logger logger = LoggerFactory
			.getLogger(JacksonCustomMapperProvider.class);

	public final static Annotations[] BASIC_ANNOTATIONS = { Annotations.JACKSON };
	JsonMapperConfigurator _mapperConfig;

	public JacksonCustomMapperProvider() {
		logger.info("JacksonCustomMapperProvider installed");
		_mapperConfig = new JsonMapperConfigurator(new ObjectMapper(),
				BASIC_ANNOTATIONS);
		_mapperConfig.setAnnotationsToUse(BASIC_ANNOTATIONS);
		// do configuration of mapper here
		_mapperConfig
				.configure(SerializationFeature.INDENT_OUTPUT, true);
		_mapperConfig.configure(
		        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> aClass) {
		return _mapperConfig.getConfiguredMapper();
	}

	public JsonMapperConfigurator getConfigurator() {
		return _mapperConfig;
	}
}
