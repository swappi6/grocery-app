package org.grocery.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedisConfiguration {
	
	@JsonProperty("hostname")
	private String hostname;
	@JsonProperty("port")
	private int port;
	@JsonProperty("max_total")
	private Integer maxTotal;
	@JsonProperty("max_idle")
	private Integer maxIdle;
	@JsonProperty("connection_timeout")
	private Integer connectionTimeout;
	@JsonProperty("read_timeout")
	private Integer readTimeout;
	
	public RedisConfiguration() {
	}

	public RedisConfiguration(String hostname, int port, Integer maxTotal, Integer maxIdle,
			Integer connectionTimeout, Integer readTimeout) {
		super();
		this.hostname = hostname;
		this.port = port;
		this.maxTotal = maxTotal;
		this.maxIdle = maxIdle;
		this.connectionTimeout = connectionTimeout;
		this.readTimeout = readTimeout;
	}

}
