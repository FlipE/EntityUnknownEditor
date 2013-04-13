package io;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import models.FloorCell;
import models.Quad;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FloorIO {
	
	public FloorCell[][] readXML(String xml) {
		FloorCell[][] floor = new FloorCell[1][1];
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            // Get the width and height of the floor
            Element doc = dom.getDocumentElement();
            int width = Integer.parseInt(doc.getAttribute("width"));
            int height = Integer.parseInt(doc.getAttribute("height"));
            
            // create the floor
            floor = new FloorCell[width][height];
            
            // get all the cell nodes
            NodeList cellNodes = doc.getElementsByTagName("cell");
            
            for(int i = 0; i < cellNodes.getLength(); i+=1) {
            	// with the current cell
            	Node cellNode = cellNodes.item(i);
            	
            	// get the type, x and y attributes
            	Element cellEle = (Element) cellNode;
            	int type = Integer.parseInt(cellEle.getAttribute("type"));
            	int x = Integer.parseInt(cellEle.getAttribute("x"));
            	int y = Integer.parseInt(cellEle.getAttribute("y"));
            	
            	// create a new FloorCell
            	FloorCell floorCell = new FloorCell(x, y, type);
            	            	
            	// get the quads in the cell
            	NodeList quadNodes = cellEle.getElementsByTagName("quad");
            	
            	// with the quads            	
            	for(int j = 0; j < quadNodes.getLength(); j+=1) {
            		            		
            		// get the current quad
            		Node quadNode = quadNodes.item(j);
            		            		
            		// create a quad and texture
            		Quad quad = null;
            		int texture = 0;
            		            		
            		if(quadNode.hasChildNodes()) {
            			
            			Element quadEle = (Element) quadNode;
            			  			
	            		// get the first vertices element | This would be used if more than one set of vertices per quad
	                	// Node vertexNode = quadEle.getElementsByTagName("vertices").item(0);
	            		               	
	                	// we need a float array for the vertices
	                	float[] vertices = new float[24];	                	
	                	
	                	// counter for which vertex property we are adding
	                	int v = 0;	                	
	                	
	                	NodeList vertexChildList;
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("bottomleft").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				vertices[v] = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                			}
	                			v += 1;
	                		}	                			                		
	                	}	                	
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("topleft").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				vertices[v] = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                			}
	                			v += 1;
	                		}	                			                		
	                	}
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("bottomright").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				vertices[v] = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                			}
	                			v += 1;
	                		}	                			                		
	                	}
	                	
	                	// add the bottom left 	                		                		
	                	vertexChildList = quadEle.getElementsByTagName("topright").item(0).getChildNodes();
	                	for(int k = 0; k < vertexChildList.getLength(); k += 1) {
	                		
	                		// get the child at k
	                		Node childNode = vertexChildList.item(k);
	                		
	                		// if the child is an element node (not #text)
	                		if(childNode.getNodeType() == Node.ELEMENT_NODE) {
	                			if(v < vertices.length) {
	                				vertices[v] = Float.parseFloat(childNode.getFirstChild().getNodeValue());
	                			}
	                			v += 1;
	                		}	                			                		
	                	}
	                	  	
	                	
	                	// get the texture	                	
            			Node textureNode = quadEle.getElementsByTagName("texture").item(0);
	                	texture = Integer.parseInt(textureNode.getFirstChild().getNodeValue());
	                		                	
	                	// create a quad and add it to the cell
	                	quad = new Quad(vertices);
	                	
            		}
            		
            		// add the quad to the cell
                	floorCell.setQuad(j, quad);
                	
                	// set the texture
                	floorCell.setTexture(j, texture);
            		
            	}            	
            	
            	// add the new cell to the Floor
            	floor[x][y] = floorCell;
            	
            }            

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        floor.toString();
        
        return floor;
        
    }	
	
	/**
	 * Saves a floor to XML. Parsing floor cells with their quads and textures.
	 * 
	 * The DTD:
	 * 
	 *  <!DOCTYPE floor [
	 *  <!ELEMENT floor (cell+)>
	 *  <!ELEMENT cell (quad+)>
	 *  <!ELEMENT quad (vertices, texture)>
	 *  <!ELEMENT vertices (bottomleft, topleft, bottomright, topright)>
	 *  <!ELEMENT bottomleft (x, y, z, color, u, v)>
	 *  <!ELEMENT topleft (x, y, z, color, u, v)>
	 *  <!ELEMENT bottomright (x, y, z, color, u, v)>
	 *  <!ELEMENT topright (x, y, z, color, u, v)>
	 *  <!ELEMENT x (#PCDATA)>
	 *  <!ELEMENT y (#PCDATA)>
	 *  <!ELEMENT z (#PCDATA)>
	 *  <!ELEMENT color (#PCDATA)>
	 *  <!ELEMENT u (#PCDATA)>
	 *  <!ELEMENT v (#PCDATA)>
	 *  
	 *  <!ATTLIST floor width CDATA #REQUIRED>
	 *  <!ATTLIST floor height CDATA #REQUIRED>
	 *  <!ATTLIST cell x CDATA #REQUIRED>
	 *  <!ATTLIST cell y CDATA #REQUIRED>
	 *  <!ATTLIST cell type (floor|ceiling|blocked) #REQUIRED>
	 *  <!ATTLIST quad index CDATA #IMPLIED>
	 *  
	 *  ]>
	 * 
	 * Example output showing a ceiling tile in a 1x1 floor:
	 * 
	 * <?xml version="1.0" encoding="UTF-8" standalone="no"?>
	 * <!DOCTYPE floor SYSTEM "floor.dtd">
	 * <floor height="1" width="1">
	 *     <cell type="1" x="0" y="0">
	 *         <quad index="0"/>
	 *         <quad index="1">
	 *             <vertices>
	 *                <bottomleft>
	 *                     <x>0.0</x>
	 *                     <y>0.0</y>
	 *                     <z>1.0</z>
	 *                     <color>-1.7014117E38</color>
	 *                     <u>1.0</u>
	 *                     <v>1.0</v>
	 *                 </bottomleft>
	 *                 <topleft>
	 *                     <x>0.0</x>
	 *                     <y>1.0</y>
	 *                     <z>1.0</z>
	 *                     <color>-1.7014117E38</color>
	 *                     <u>1.0</u>
	 *                     <v>0.0</v>
	 *                 </topleft>
	 *                 <bottomright>
	 *                     <x>1.0</x>
	 *                     <y>0.0</y>
	 *                     <z>1.0</z>
	 *                     <color>-1.7014117E38</color>
	 *                     <u>0.0</u>
	 *                     <v>1.0</v>
	 *                 </bottomright>
	 *                 <topright>
	 *                     <x>1.0</x>
	 *                     <y>1.0</y>
	 *                     <z>1.0</z>
	 *                     <color>-1.7014117E38</color>
	 *                     <u>0.0</u>
	 *                     <v>0.0</v>
	 *                 </topright>
	 *             </vertices>
	 *             <texture>0</texture>
	 *         </quad>
	 *         <quad index="2"/>
	 *         <quad index="3"/>
	 *         <quad index="4"/>
	 *         <quad index="5"/>
	 *     </cell>
	 *  </floor>
	 *  
	 * 
	 * @param floor A 2D array of FloorCells
	 * @param xml The path and name of the file to save to
	 * @return true if the save is successful, false otherwise
	 */
	public boolean saveToXML(FloorCell[][] floor, String xml) {
		boolean success = false;
	    Document dom;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement("floor");
	        rootEle.setAttribute("width", floor.length + "");
	        rootEle.setAttribute("height", floor[0].length + "");
	        
	        // for every cell in the floor
	        for (int x = 0; x < floor.length; x += 1) {
	        	for (int y = 0; y < floor[0].length; y += 1) {
								
					FloorCell cell = floor[x][y];
	        	
		        	// add the cell to the floor
		        	Element cellEle = dom.createElement("cell");
		        	cellEle.setAttribute("x", x + "");
		        	cellEle.setAttribute("y", y + "");
		        	cellEle.setAttribute("type", cell.getType() + "");
			        rootEle.appendChild(cellEle);
		        	
			        // for every quad in the cell
			        Quad[] quads = cell.getQuads();
		        	for(int i = 0; i < quads.length; i+=1) {
		        		
		        		// add the quad to the cell
		        		Element quadEle = dom.createElement("quad");
		        		quadEle.setAttribute("index", i + "");
		        		cellEle.appendChild(quadEle);
		        		
		        		if (quads[i] != null) {
		        			
		        			Element verticesEle = dom.createElement("vertices");
			        		quadEle.appendChild(verticesEle);
		        			
			        		float[] vertices = quads[i].getVertices();
			        		
		        			Element xEle;
		        			Element yEle;
		        			Element zEle;
		        			Element colorEle;
		        			Element uEle;
		        			Element vEle;
		        			
		        			// bottom left: x y z color u v
		        			Element blEle = dom.createElement("bottomleft");
		        			verticesEle.appendChild(blEle);
		        				
		        				xEle = dom.createElement("x");
		        				xEle.appendChild(dom.createTextNode(vertices[0] + ""));
		        				blEle.appendChild(xEle);
		        				
		        				yEle = dom.createElement("y");
		        				yEle.appendChild(dom.createTextNode(vertices[1] + ""));
		        				blEle.appendChild(yEle);
		        			
		        				zEle = dom.createElement("z");
		        				zEle.appendChild(dom.createTextNode(vertices[2] + ""));
		        				blEle.appendChild(zEle);
		        				
		        				colorEle = dom.createElement("color");
		        				colorEle.appendChild(dom.createTextNode(vertices[3] + ""));
		        				blEle.appendChild(colorEle);
		        				
		        				uEle = dom.createElement("u");
		        				uEle.appendChild(dom.createTextNode(vertices[4] + ""));
		        				blEle.appendChild(uEle);
		        				
		        				vEle = dom.createElement("v");
		        				vEle.appendChild(dom.createTextNode(vertices[5] + ""));
		        				blEle.appendChild(vEle);
		        				
		        				
	        				// top left: x y z color u v
		        			Element tlEle = dom.createElement("topleft");
		        			verticesEle.appendChild(tlEle);
		        				
		        				xEle = dom.createElement("x");
		        				xEle.appendChild(dom.createTextNode(vertices[6] + ""));
		        				tlEle.appendChild(xEle);
		        				
		        				yEle = dom.createElement("y");
		        				yEle.appendChild(dom.createTextNode(vertices[7] + ""));
		        				tlEle.appendChild(yEle);
		        			
		        				zEle = dom.createElement("z");
		        				zEle.appendChild(dom.createTextNode(vertices[8] + ""));
		        				tlEle.appendChild(zEle);
		        				
		        				colorEle = dom.createElement("color");
		        				colorEle.appendChild(dom.createTextNode(vertices[9] + ""));
		        				tlEle.appendChild(colorEle);
		        				
		        				uEle = dom.createElement("u");
		        				uEle.appendChild(dom.createTextNode(vertices[10] + ""));
		        				tlEle.appendChild(uEle);
		        				
		        				vEle = dom.createElement("v");
		        				vEle.appendChild(dom.createTextNode(vertices[11] + ""));
		        				tlEle.appendChild(vEle);
			        		
			        		
	        				// bottom right: x y z color u v
		        			Element brEle = dom.createElement("bottomright");
		        			verticesEle.appendChild(brEle);
		        				
		        				xEle = dom.createElement("x");
		        				xEle.appendChild(dom.createTextNode(vertices[12] + ""));
		        				brEle.appendChild(xEle);
		        				
		        				yEle = dom.createElement("y");
		        				yEle.appendChild(dom.createTextNode(vertices[13] + ""));
		        				brEle.appendChild(yEle);
		        			
		        				zEle = dom.createElement("z");
		        				zEle.appendChild(dom.createTextNode(vertices[14] + ""));
		        				brEle.appendChild(zEle);
		        				
		        				colorEle = dom.createElement("color");
		        				colorEle.appendChild(dom.createTextNode(vertices[15] + ""));
		        				brEle.appendChild(colorEle);
		        				
		        				uEle = dom.createElement("u");
		        				uEle.appendChild(dom.createTextNode(vertices[16] + ""));
		        				brEle.appendChild(uEle);
		        				
		        				vEle = dom.createElement("v");
		        				vEle.appendChild(dom.createTextNode(vertices[17] + ""));
		        				brEle.appendChild(vEle);	
		        			
			        		
		        				
	        				// top right: x y z color u v
		        			Element trEle = dom.createElement("topright");
		        			verticesEle.appendChild(trEle);
		        				
		        				xEle = dom.createElement("x");
		        				xEle.appendChild(dom.createTextNode(vertices[18] + ""));
		        				trEle.appendChild(xEle);
		        				
		        				yEle = dom.createElement("y");
		        				yEle.appendChild(dom.createTextNode(vertices[19] + ""));
		        				trEle.appendChild(yEle);
		        			
		        				zEle = dom.createElement("z");
		        				zEle.appendChild(dom.createTextNode(vertices[20] + ""));
		        				trEle.appendChild(zEle);
		        				
		        				colorEle = dom.createElement("color");
		        				colorEle.appendChild(dom.createTextNode(vertices[21] + ""));
		        				trEle.appendChild(colorEle);
		        				
		        				uEle = dom.createElement("u");
		        				uEle.appendChild(dom.createTextNode(vertices[22] + ""));
		        				trEle.appendChild(uEle);
		        				
		        				vEle = dom.createElement("v");
		        				vEle.appendChild(dom.createTextNode(vertices[23] + ""));
		        				trEle.appendChild(vEle);
		        				
			        		// the texture
		        			Element textureEle = dom.createElement("texture");		        			
		        			textureEle.appendChild(dom.createTextNode(cell.getTexture(i) + ""));
		        			quadEle.appendChild(textureEle);
		        		}
		        	}
				}
	        }

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "floor.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            DOMSource ds = new DOMSource(dom);
	            FileOutputStream fos = new FileOutputStream(xml);
	            StreamResult sr = new StreamResult(fos);
	            
	            tr.transform(ds, sr);
	            
	            // Close this to stop leak
	            fos.close();
	            tr = null;
	            sr = null;
	            ds = null;
	            
	            // level saved
	            success = true;
	            
	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	        
	        dom = null;
	        
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	    
	    if(success) {
	    	System.out.println("Saved level " + xml);
	    }
	    else {
	    	System.out.println("Failed to save level " + xml);
	    }
	    
	    // This seems
	    System.gc();
	    
	    return success;
	}
	
}