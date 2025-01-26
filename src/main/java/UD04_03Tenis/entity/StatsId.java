package UD04_03Tenis.entity;
// Generated 26 ene 2025 12:30:14 by Hibernate Tools 4.3.6.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class StatsId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "season", nullable = false, length = 5)
	private String season;
	@Column(name = "player", nullable = false)
	private int player;

	public StatsId() {}

	public StatsId(String season, int player) {
		this.season = season;
		this.player = player;}

	public String getSeason() {
		return this.season;}

	public void setSeason(String season) {
		this.season = season;}

	public int getPlayer() {
		return this.player;}

	public void setPlayer(int player) {
		this.player = player;}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StatsId))
			return false;
		StatsId castOther = (StatsId) other;

		return ((this.getSeason() == castOther.getSeason()) 
				|| (this.getSeason() != null
				&& castOther.getSeason() != null 
				&& this.getSeason().equals(castOther.getSeason())))
				&& (this.getPlayer() == castOther.getPlayer());}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (
				getSeason() == null 
						? 0 
						: this.getSeason().hashCode());
		result = 37 * result + this.getPlayer();
		return result;}
}