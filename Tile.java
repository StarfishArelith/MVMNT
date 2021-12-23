public class Tile
{
	private String displayed;
	private String terrain;
	private String entity = "";
	//private Entity entity;
	//private Item[] items;
	
	public Tile(String terrain)
	{
		this.terrain = terrain;
		displayed = terrain;
	}
	
	public void setDisplayed()
	{
		if(entity.equals("") == true)
		{
			displayed = terrain;
		}
		else
		{
			displayed = entity;
		}
	}
	
	public void setTerrain(String terrain)
	{
		this.terrain = terrain;
	}
	
	public void setEntity(String entity)
	{
		this.entity = entity;
		setDisplayed();
	}
	
	public String getDisplayed()
	{
		return displayed;
	}
	
	public String getEntity()
	{
		return entity;
	}
	
	public String getTerrain()
	{
		return terrain;
	}
}