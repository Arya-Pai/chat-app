package com.project.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.project.model.User;
@Repository
public class SearchUser implements SearchUserRepo{
	@Autowired
	MongoClient client;
	@Autowired
	MongoConverter converter;
	
	@Override
	public List<User> findAllUser(String text) {
		final List<User> users=new ArrayList<>();
		
		MongoDatabase database = client.getDatabase("chat-app");
		MongoCollection<Document> collection = database.getCollection("users");
		AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
		    new Document("index", "user_names")
		            .append("text", 
		    new Document("query", text)
		                .append("path", Arrays.asList("email", "username", "role")))), 
		    new Document("$sort", 
		    new Document("username", 1L)
		            .append("email", 1L))));

			result.forEach(doc->users.add(converter.read(User.class, doc)));

		
		return users;
	}
	
}
