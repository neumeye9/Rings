package com.krista.rings.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="teams")
public class Team {

		@Id
		@GeneratedValue
		private Long id;
		
		@Column
		private String guild;
		
		@Column
		private int size;
		
		@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
		@JoinTable(
				name = "teams_users",
				joinColumns = @JoinColumn(name = "team_id"),
				inverseJoinColumns = @JoinColumn(name = "user_id"))
	
		private List<User> users;
		
		public Team() {
			
		}
		
		public Team(String guild, int size) {
			this.guild = guild;
			this.size = size;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getGuild() {
			return guild;
		}

		public void setGuild(String guild) {
			this.guild = guild;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}

		
}
