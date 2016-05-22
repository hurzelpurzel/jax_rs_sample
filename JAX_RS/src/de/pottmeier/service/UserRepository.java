package de.pottmeier.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;



@Path("/userservice")
@Lock(LockType.READ)
@Singleton
public class UserRepository {

	private Map<Integer, UserEntity> stock;
	private int nextId;

	public UserRepository() {
		super();
		this.stock = new HashMap<Integer, UserEntity>();
		this.nextId = 1;
		this.init();
	}

	
	public void init(){
		this._create(new UserEntity("Pottmeier", "L", "l.pottmeier@gmx.de"));
		this._create(new UserEntity("Pottmeier", "A", "a.pottmeier@gmail.com"));
	}
	
	private int _create(UserEntity tmp) {
		
		UserEntity res = new UserEntity(this.nextId, tmp.getName(), tmp.getFirstname(), tmp.getEmail());
		this.stock.put(res.getId(),res);
		this.nextId++;
		return res.getId();
	}


	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserEntity> getAll() {
		return new ArrayList<UserEntity>(this.stock.values());
	}

	
	@Lock(LockType.WRITE)
	@Path("/users")
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserEntity tmp, @Context UriInfo uriInfo) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(this._create(tmp)));
        return Response.created(builder.build()).build();
    }

	@GET
	@Path("/users/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id")int id,@Context UriInfo uriInfo) {
		if (this.stock.containsKey(id)) {
			UserEntity res= this.stock.get(id);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	        builder.path(Integer.toString(id));
	        return Response.ok(res,MediaType.APPLICATION_JSON).build();
		}
		return Response.noContent().build();
	}

	@Lock(LockType.WRITE)
	@DELETE
	@Path("/users/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id,@Context UriInfo uriInfo) {
		if (this.stock.containsKey(id)) {
			this.stock.remove(id);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	        builder.path(Integer.toString(id));
	        return Response.noContent().build();
		}
		return Response.notModified().build();
	}
	
	@Lock(LockType.WRITE)
	@PUT
	@Path("/users/{id}")
	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id,UserEntity changed,@Context UriInfo uriInfo) {
		changed.setId(id);
		if (this.stock.containsKey(id)) {
			this.stock.put(id, changed);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	        builder.path(Integer.toString(id));
	        return Response.ok(builder.build()).build();
			
		} else {
			if(id <= this.nextId){
				UserEntity res = new UserEntity(id, changed.getName(), changed.getFirstname(), changed.getEmail());
				this.stock.put(res.getId(),res);
			}else{
				this._create(changed);

			}
			
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	        builder.path(Integer.toString(id));
	        return Response.created(builder.build()).build();
			
		}
		
		
	}

}
