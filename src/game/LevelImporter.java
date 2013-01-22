package game;

import game.data.Layer;
import game.data.Level;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LevelImporter implements Converter{

	@Override
	public boolean canConvert(Class c){
		return c.equals(Level.class);
	}

	//converts the object into xml
	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context){
		//don't need to convert levels into XML, we can do this by using the Gleed2D program.
		/**Level level = (Level) obj;
		writer.startNode("Name");
		writer.setValue(level.getName());
		writer.endNode();*/
	}
	
	private Layer createLayer(HierarchicalStreamReader reader){
		
		return null;
	}
	
	private void getLevelProperties(Level level, HierarchicalStreamReader reader){
		reader.moveDown();
		
		reader.moveDown();
		level.setName(reader.getValue());
		reader.moveUp();
		
		reader.moveDown();
		level.setId(Integer.parseInt(reader.getValue()));
		reader.moveUp();
		
		reader.moveDown();
		
		reader.moveUp();
		
		
		
		reader.moveUp();
	}

	//reads from xml and turns it into an object
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){
		Level level = new Level();
		System.out.println(reader.getNodeName());
		reader.moveDown();	//go into level properties
		System.out.println(reader.getNodeName());
		reader.moveDown();	//go into name
		System.out.println(reader.getNodeName());
		level.setName(reader.getValue());
		reader.moveUp();	//leave name
		System.out.println(reader.getNodeName());
		reader.moveUp();	//leave level properties
		System.out.println(reader.getNodeName());
		reader.moveDown();	//go into test
		System.out.println(reader.getNodeName());
		reader.moveUp();	//leave test
		return level;
	}

}
