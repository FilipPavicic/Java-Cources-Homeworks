package hr.fer.zemris.java.p12.model;

public class PollOptions implements Comparable<PollOptions> {
	long id;
	long pollID;
	String optionTitle;
	String optionLink;
	int votesCount;
	public PollOptions(long id, long pollID, String optionTitle, String optionLink, int votesCount) {
		super();
		this.id = id;
		this.pollID = pollID;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPollID() {
		return pollID;
	}
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	public String getOptionLink() {
		return optionLink;
	}
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}
	public int getVotesCount() {
		return votesCount;
	}
	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollOptions other = (PollOptions) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public int compareTo(PollOptions o) {
		return Integer.compare(this.votesCount, o.votesCount);
	}
}
