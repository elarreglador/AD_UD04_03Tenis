package UD04_03Tenis.entity;
// Generated 26 ene 2025 12:30:14 by Hibernate Tools 4.3.6.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "stats", catalog = "NBA")
public class Stats implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "season", column = @Column
					(name = "season", nullable = false, length = 5)),
			@AttributeOverride(name = "player", column = @Column
					(name = "player", nullable = false)) })
	private StatsId id;
	@Column(name = "Points_per_match", precision = 12, scale = 0)
	private Float pointsPerMatch;
	@Column(name = "Assistances_per_match", precision = 12, scale = 0)
	private Float assistancesPerMatch;
	@Column(name = "Blocks_per_match", precision = 12, scale = 0)
	private Float blocksPerMatch;
	@Column(name = "Rebound_per_match", precision = 12, scale = 0)
	private Float reboundPerMatch;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player", referencedColumnName = "code", insertable = false, updatable = false)
	private Players players;

	public Stats() {}

	public Stats(StatsId id) {
		this.id = id;}

	public Stats(StatsId id, Float pointsPerMatch, 
			Float assistancesPerMatch, Float blocksPerMatch,
			Float reboundPerMatch) {
		this.id = id;
		this.pointsPerMatch = pointsPerMatch;
		this.assistancesPerMatch = assistancesPerMatch;
		this.blocksPerMatch = blocksPerMatch;
		this.reboundPerMatch = reboundPerMatch;}

	
	public StatsId getId() {
		return this.id;}

	public void setId(StatsId id) {
		this.id = id;}

	public Float getPointsPerMatch() {
		return this.pointsPerMatch;}

	public void setPointsPerMatch(Float pointsPerMatch) {
		this.pointsPerMatch = pointsPerMatch;}

	public Float getAssistancesPerMatch() {
		return this.assistancesPerMatch;}

	public void setAssistancesPerMatch(Float assistancesPerMatch) {
		this.assistancesPerMatch = assistancesPerMatch;}

	public Float getBlocksPerMatch() {
		return this.blocksPerMatch;}

	public void setBlocksPerMatch(Float blocksPerMatch) {
		this.blocksPerMatch = blocksPerMatch;}

	public Float getReboundPerMatch() {
		return this.reboundPerMatch;}

	public void setReboundPerMatch(Float reboundPerMatch) {
		this.reboundPerMatch = reboundPerMatch;}
	
	@Override
	public String toString() {
		return "ID = " + this.getId() +
				"asistenc. por partido = "+ this.getAssistancesPerMatch()+
				"\n bloqueos por partido = " + this.getBlocksPerMatch() + 
				"\n puntos por partido = " + this.getPointsPerMatch() + 
				"\n rebotes por partido = " + this.getReboundPerMatch();
	}
}