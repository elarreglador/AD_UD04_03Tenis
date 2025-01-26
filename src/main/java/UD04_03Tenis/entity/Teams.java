package UD04_03Tenis.entity;
// Generated 26 ene 2025 12:30:14 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teams", catalog = "NBA")
public class Teams implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "Name", unique = true, nullable = false, length = 20)
	private String name;
	@Column(name = "City", length = 20)
	private String city;
	@Column(name = "Conference", length = 4)
	private String conference;
	@Column(name = "Division", length = 9)
	private String division;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "teamsByVisitorTeam")
	private Set<Matches> matchesesForVisitorTeam = new HashSet<Matches>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "teamsByLocalTeam")
	private Set<Matches> matchesesForLocalTeam = new HashSet<Matches>(0);

	public Teams() {}

	public Teams(String name) {
		this.name = name;}

	public Teams(String name, String city, String conference, 
			String division, Set<Matches> matchesesForVisitorTeam,
			Set<Matches> matchesesForLocalTeam) {
		this.name = name;
		this.city = city;
		this.conference = conference;
		this.division = division;
		this.matchesesForVisitorTeam = matchesesForVisitorTeam;
		this.matchesesForLocalTeam = matchesesForLocalTeam;}

	
	public String getName() {
		return this.name;}

	public void setName(String name) {
		this.name = name;}

	public String getCity() {
		return this.city;}

	public void setCity(String city) {
		this.city = city;}

	public String getConference() {
		return this.conference;}

	public void setConference(String conference) {
		this.conference = conference;}

	public String getDivision() {
		return this.division;}

	public void setDivision(String division) {
		this.division = division;}

	public Set<Matches> getMatchesesForVisitorTeam() {
		return this.matchesesForVisitorTeam;}

	public void setMatchesesForVisitorTeam(
			Set<Matches> matchesesForVisitorTeam) {
		this.matchesesForVisitorTeam = matchesesForVisitorTeam;}

	public Set<Matches> getMatchesesForLocalTeam() {
		return this.matchesesForLocalTeam;}

	public void setMatchesesForLocalTeam(
			Set<Matches> matchesesForLocalTeam) {
		this.matchesesForLocalTeam = matchesesForLocalTeam;}
}
