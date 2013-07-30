package com.appthwack.appthwack;

import java.lang.Boolean;
import java.util.List;
import java.io.File;

import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import com.appthwack.appthwack.AppThwackException;


/**
 * Client for the AppThwack REST API.
 * @author ahawker
 *
 */
public class AppThwackApi {
	
	private static final String DOMAIN = "https://appthwack.com";
	private static final String ROOT = "/api";
	
	private Client client;
	private WebResource root;
	
	/**
	 * Create a new client using the given API Key.
	 * @param apiKey API Key tied to AppThwack account.
	 */
	public AppThwackApi(String apiKey) {
		this(apiKey, DOMAIN);
	}
	
	/**
	 * Create a new client using the given API Key using a specific domain.
	 * @param apiKey API Key tied to AppThwack account.
	 * @param domain HTTP domain of AppThwack.
	 */
	public AppThwackApi(String apiKey, String domain) {
		this(apiKey, domain, ROOT);
	}
	
	/**
	 * Create a new client using the given API Key, using a specific domain and API endpoint.
	 * @param apiKey API Key tied to AppThwack account.
	 * @param domain HTTP domain of AppThwack (default: https://appthwack.com).
	 * @param resourceRoot URL endpoint for the API (default: /api).
	 */
	public AppThwackApi(String apiKey, String domain, String resourceRoot) {
		client = getClient(apiKey);
		root = client.resource(domain + resourceRoot);
	}
	
	/**
	 * Returns a configured Jersey client used to communicate with AppThwack.
	 * @param apiKey API Key for your user account. See https://appthwack/user/profile.
	 * @return fully configured client object.
	 */
	private Client getClient(String apiKey) {
		client = Client.create(getClientConfig());
		client.addFilter(new HTTPBasicAuthFilter(apiKey, ""));
		return client;
	}
	
	/**
	 * Returns a client configuration with the proper settings/features for
	 * communication with AppThwack.
	 * @return fully created client configuration object.
	 */
	private ClientConfig getClientConfig() {
		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJaxbJsonProvider.class);
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		config.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, Boolean.TRUE);
		return config;
	}
	
	/**
	 * Get an individual project for the given name.
	 * @param name name of the project we want.
	 * @return project with the given name or null.
	 */
	public AppThwackProject getProject(String name) {
		List<AppThwackProject> projects = getProjects();
		for(AppThwackProject p : projects) {
			if(p.name.equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Get an individual project for the given id.
	 * @param id id of the project we want.
	 * @return project with the given id or null.
	 */
	public AppThwackProject getProject(Integer id) {
		List<AppThwackProject> projects = getProjects();
		for(AppThwackProject p : projects) {
			if(p.id.equals(id)) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Get a list of all projects tied to this account.
	 * @return list of projects.
	 */
	public List<AppThwackProject> getProjects() {
		List<AppThwackProject> projects = root.path("project").get(new GenericType<List<AppThwackProject>>(){});
		for(AppThwackProject p : projects) {
			p.setRoot(root);
		}
		return projects;
	}
	
	/**
	 * Upload file located at the given path to AppThwack.
	 * @param path fully-qualified file path.
	 * @return reference to remote file on AppThwack.
	 */
	public AppThwackFile uploadFile(String path) throws AppThwackException {
		File file = new File(path);
		String name = file.getName();
		return uploadFile(file, name);
	}
	
	/**
	 * Upload file located at the given path to AppThwack and specify its name.
	 * @param path fully-qualified file path.
	 * @param name name of the file once uploaded.
	 * @return reference to remote file on AppThwack.
	 */
	public AppThwackFile uploadFile(String path, String name) throws AppThwackException {
		File file = new File(path);
		return uploadFile(file, name);
	}
	
	/**
	 * Upload the given file object to AppThwack.
	 * @param file file object to upload.
	 * @return reference to remote file on AppThwack.
	 */
	public AppThwackFile uploadFile(File file) throws AppThwackException {
		String name = file.getName();
		return uploadFile(file, name);
	}
	
	/**
	 * Upload the given file object to AppThwack and specify its name.
	 * @param file file object to upload.
	 * @param name name of the file once uploaded.
	 * @return reference to remote file on AppThwack.
	 */
	public AppThwackFile uploadFile(File file, String name) throws AppThwackException {
		if(file == null) {
			throw new AppThwackException("file cannot be null");
		}
		if(name == null || name.isEmpty()) {
			throw new AppThwackException("name cannot be null or empty");
		}
		if(!file.exists()) {
			throw new AppThwackException("file does not exist");
		}
		if(!file.isFile()) {
			throw new AppThwackException("file cannot be a directory");
		}
		
		FormDataMultiPart form = new FormDataMultiPart();
		form.field("name", name);
		form.bodyPart(new FileDataBodyPart("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		
		return root.path("file").type(MediaType.MULTIPART_FORM_DATA).post(AppThwackFile.class, form);
	}
}
