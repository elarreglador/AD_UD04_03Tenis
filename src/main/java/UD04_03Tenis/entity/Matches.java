package UD04_03Tenis.entity;
// Generated 26 ene 2025 12:30:14 by Hibernate Tools 4.3.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "matches", catalog = "NBA")
public class Matches implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "code", unique = true, nullable = false)
	private int code;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visitor_team")
	private Teams teamsByVisitorTeam;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "local_team")
	private Teams teamsByLocalTeam;
	@Column(name = "points_local")
	private Integer pointsLocal;
	@Column(name = "points_visitor")
	private Integer pointsVisitor;
	@Column(name = "season", length = 5)
	private String season;

	public Matches() {}

	public Matches(int code) {
		this.code = code;}

	public Matches(int code, Teams teamsByVisitorTeam, 
			Teams teamsByLocalTeam, Integer pointsLocal,
			Integer pointsVisitor, String season) {
		this.code = code;
		this.teamsByVisitorTeam = teamsByVisitorTeam;
		this.teamsByLocalTeam = teamsByLocalTeam;
		this.pointsLocal = pointsLocal;
		this.pointsVisitor = pointsVisitor;
		this.season = season;}

	
	public int getCode() {
		return this.code;}

	public void setCode(int code) {
		this.code = code;}


	public Teams getTeamsByVisitorTeam() {
		return this.teamsByVisitorTeam;}

	public void setTeamsByVisitorTeam(Teams teamsByVisitorTeam) {
		this.teamsByVisitorTeam = teamsByVisitorTeam;}


	public Teams getTeamsByLocalTeam() {
		return this.teamsByLocalTeam;}

	public void setTeamsByLocalTeam(Teams teamsByLocalTeam) {
		this.teamsByLocalTeam = teamsByLocalTeam;}

	public Integer getPointsLocal() {
		return this.pointsLocal;}

	public void setPointsLocal(Integer pointsLocal) {
		this.pointsLocal = pointsLocal;}

	public Integer getPointsVisitor() {
		return this.pointsVisitor;}

	public void setPointsVisitor(Integer pointsVisitor) {
		this.pointsVisitor = pointsVisitor;}

	public String getSeason() {
		return this.season;}

	public void setSeason(String season) {
		this.season = season;}

}
