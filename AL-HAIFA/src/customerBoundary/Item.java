/**
 *
 */
package customerBoundary;

/**
 * @author Guy
 *
 */
public class Item {
	private String id1;
	private String id2;
    private String description;


    public Item(String id1, String id2, String description) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.description = description;
	}


	public String getId1() {
		return id1;
	}


	public String getId2() {
		return id2;
	}


	public String getDescription() {
		return description;
	}


	public void setId1(String id1) {
		this.id1 = id1;
	}


	public void setId2(String id2) {
		this.id2 = id2;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
		result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Item))
			return false;
		Item other = (Item) obj;
		if (id1 == null) {
			if (other.id1 != null)
				return false;
		} else if (!id1.equals(other.id1))
			return false;
		if (id2 == null) {
			if (other.id2 != null)
				return false;
		} else if (!id2.equals(other.id2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s]", description);
	}
}
