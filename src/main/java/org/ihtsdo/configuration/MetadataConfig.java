/**
 * Copyright (c) 2016 International Health Terminology Standards Development
 * Organisation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ihtsdo.configuration;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * The Class MetadataConfig.
 */
public class MetadataConfig {


	/** The R f2_ fsn. */
	private String RF2_FSN;
	
	/** The R f2_ synonym. */
	private String RF2_SYNONYM;
	
	/** The R f2_ preferred. */
	private String RF2_PREFERRED;
	
	/** The R f2_ acceptable. */
	private String RF2_ACCEPTABLE;
	
	/** The R f2_ e n_ refset. */
	private String RF2_EN_REFSET;

	/** The R f2_ ic s_ significant. */
	private String RF2_ICS_SIGNIFICANT;

	/** The R f2_ de f_ statu s_ primitive. */
	private String RF2_DEF_STATUS_PRIMITIVE;

	/** The R f2_ ct v3 i d_ refsetid. */
	private String RF2_CTV3ID_REFSETID;
	
	/** The R f2_ snomedi d_ refsetid. */
	private String RF2_SNOMEDID_REFSETID;

	/** The R f1_ fsn. */
	private String RF1_FSN;
	
	/** The R f1_ synonym. */
	private String RF1_SYNONYM;
	
	/** The R f1_ preferred. */
	private String RF1_PREFERRED;

	/** The R f1_ e n_ subset. */
	private String RF1_EN_SUBSET;
	
	/** The R f1_ g b_ subset. */
	private String RF1_GB_SUBSET;
	
	/** The R f1_ gblan g_ code. */
	private String RF1_GBLANG_CODE;
	
	/** The R f1_ uslan g_ code. */
	private String RF1_USLANG_CODE;
	
	/** The R f1_ enlan g_ code. */
	private String RF1_ENLANG_CODE;

	/** The xml config. */
	private XMLConfiguration xmlConfig;
	
	/** The R f1_ subse t_ defined. */
	private String RF1_SUBSET_DEFINED;
	
	/** The R f2_ enu s_ refset. */
	private String RF2_ENUS_REFSET;
	
	/** The R f2_ eng b_ refset. */
	private String RF2_ENGB_REFSET;

	/** The R f2_ inac t_ concep t_ refset. */
	private String RF2_INACT_CONCEPT_REFSET;
	
	/** The R f2_ inac t_ descriptio n_ refset. */
	private String RF2_INACT_DESCRIPTION_REFSET;

	/** The R f2_ refinabilit y_ refsetid. */
	private String RF2_REFINABILITY_REFSETID;
	//	refina 900000000000488004

	/** The R f2 r f1 refina map. */
	private HashMap<String,String> RF2RF1RefinaMap;
	
	/** The R f2 r f1char type map. */
	private HashMap<String,String> RF2RF1charTypeMap;
	
	/** The R f2 r f1inact stat map. */
	private HashMap<String, String> RF2RF1inactStatMap;
	
	/** The R f2 r f1 association map. */
	private HashMap<String, String> RF2RF1AssociationMap;
	
	/** The R f2_ is a_ relationship. */
	private String RF2_ISA_RELATIONSHIP;
	
	/** The R f1 r f2char type map. */
	private HashMap<String, String> RF1RF2charTypeMap;
	
	/** The R f2 modifier some. */
	private String RF2ModifierSome;
	
	/** The R f1 r f2 refina map. */
	private HashMap<String, String> RF1RF2RefinaMap;
	
	/** The R f2_ de f_ statu s_ defined. */
	private String RF2_DEF_STATUS_DEFINED;
	
	/** The R f1 r f2inact stat map. */
	private HashMap<String, String> RF1RF2inactStatMap;
	
	/** The R f2_ ic s_ nosignificant. */
	private String RF2_ICS_NOSIGNIFICANT;
	
	/** The R f1 r f2 association map. */
	private HashMap<String, String> RF1RF2AssociationMap;
	
	/** The R f1 cause inactive concept. */
	private TreeSet<String> RF1CauseInactiveConcept;
	
	/** The R f1_ is a_ relationship. */
	private String RF1_ISA_RELATIONSHIP;
	
	/** The R f2 references. */
	private HashMap<String, String[]> RF2References;
	
	/** The R f2_ c a_ refset. */
	private String RF2_CA_REFSET;
	
	/** The R f2_ e s_ refset. */
	private String RF2_ES_REFSET;
	
	/** The metadata model sctid. */
	private String metadataModelSCTID;
	
	/** The namespace cpt sctid. */
	private String namespaceCptSCTID;
	
	/** The linkage cpt sctid. */
	private String linkageCptSCTID;

	/** The R f2 description references. */
	private HashSet<String> RF2DescriptionReferences;

	/**
	 * Instantiates a new metadata config.
	 *
	 * @throws Exception the exception
	 */
	public MetadataConfig() throws Exception {

		//		this.configFile = configFile;
		File configFile= new File("config/metadata.xml");
		try {
			xmlConfig=new XMLConfiguration(configFile);
			RF2_FSN=xmlConfig.getString("rf2.descriptionType.fsn");
			RF2_SYNONYM=xmlConfig.getString("rf2.descriptionType.synonym");
			RF2_PREFERRED=xmlConfig.getString("rf2.acceptability.preferred");
			RF2_ACCEPTABLE=xmlConfig.getString("rf2.acceptability.acceptable");
			RF2_EN_REFSET=xmlConfig.getString("rf2.enRefset");
			RF2_ENUS_REFSET=xmlConfig.getString("rf2.enUSRefset");
			RF2_ENGB_REFSET=xmlConfig.getString("rf2.enGBRefset");
			RF2_CA_REFSET=xmlConfig.getString("rf2.caRefset");
			RF2_ES_REFSET=xmlConfig.getString("rf2.esRefset");

			RF2_ICS_SIGNIFICANT=xmlConfig.getString("rf2.ics.significant");
			RF2_ICS_NOSIGNIFICANT=xmlConfig.getString("rf2.ics.nosignificant");

			RF2_DEF_STATUS_PRIMITIVE=xmlConfig.getString("rf2.definitionStatus.primitive");
			RF2_DEF_STATUS_DEFINED=xmlConfig.getString("rf2.definitionStatus.defined");

			RF2_CTV3ID_REFSETID=xmlConfig.getString("rf2.ctv3idRefset");
			RF2_SNOMEDID_REFSETID=xmlConfig.getString("rf2.snomedIdRefset");

			RF2_REFINABILITY_REFSETID=xmlConfig.getString("rf2.refinabilityRefset");

			RF1_FSN=xmlConfig.getString("rf1.fsn");
			RF1_SYNONYM=xmlConfig.getString("rf1.synonym");
			RF1_PREFERRED=xmlConfig.getString("rf1.preferred");
			RF1_SUBSET_DEFINED=xmlConfig.getString("rf1.subsetDefined");
			RF1_EN_SUBSET=xmlConfig.getString("rf1.descriptionSubset.enOriginalSubsetId");
			RF1_GB_SUBSET=xmlConfig.getString("rf1.descriptionSubset.gbOriginalSubsetId");
			RF1_GBLANG_CODE=xmlConfig.getString("rf1.gbLangCode");
			RF1_USLANG_CODE=xmlConfig.getString("rf1.usLangCode");
			RF1_ENLANG_CODE=xmlConfig.getString("rf1.enLangCode");

			RF2RF1RefinaMap=new HashMap<String, String>();
			RF2RF1RefinaMap.put(xmlConfig.getString("rf2.refinability.notRefinable"),xmlConfig.getString("rf1.refinability.notRefinable"));
			RF2RF1RefinaMap.put(xmlConfig.getString("rf2.refinability.optional"),xmlConfig.getString("rf1.refinability.optional"));
			RF2RF1RefinaMap.put(xmlConfig.getString("rf2.refinability.mandatory"),xmlConfig.getString("rf1.refinability.mandatory"));

			RF1RF2RefinaMap=new HashMap<String, String>();
			RF1RF2RefinaMap.put(xmlConfig.getString("rf1.refinability.notRefinable"),xmlConfig.getString("rf2.refinability.notRefinable"));
			RF1RF2RefinaMap.put(xmlConfig.getString("rf1.refinability.optional"),xmlConfig.getString("rf2.refinability.optional"));
			RF1RF2RefinaMap.put(xmlConfig.getString("rf1.refinability.mandatory"),xmlConfig.getString("rf2.refinability.mandatory"));

			RF2RF1charTypeMap=new HashMap<String, String>();
			RF2RF1charTypeMap.put(xmlConfig.getString("rf2.characteristicType.DefiningRelationship"),xmlConfig.getString("rf1.characteristicType.DefiningRelationship"));
			RF2RF1charTypeMap.put(xmlConfig.getString("rf2.characteristicType.QualifyingRelationship"),xmlConfig.getString("rf1.characteristicType.QualifyingRelationship"));
			RF2RF1charTypeMap.put(xmlConfig.getString("rf2.characteristicType.AdditionalRelationship"),xmlConfig.getString("rf1.characteristicType.AdditionalRelationship"));

			RF1RF2charTypeMap=new HashMap<String, String>();
			RF1RF2charTypeMap.put(xmlConfig.getString("rf1.characteristicType.DefiningRelationship"),xmlConfig.getString("rf2.characteristicType.DefiningRelationship"));
			RF1RF2charTypeMap.put(xmlConfig.getString("rf1.characteristicType.QualifyingRelationship"),xmlConfig.getString("rf2.characteristicType.QualifyingRelationship"));
			RF1RF2charTypeMap.put(xmlConfig.getString("rf1.characteristicType.AdditionalRelationship"),xmlConfig.getString("rf2.characteristicType.AdditionalRelationship"));

			RF2ModifierSome=xmlConfig.getString("rf2.modifier.some");

			RF2_INACT_CONCEPT_REFSET=xmlConfig.getString("rf2.conceptInactivationRefset");
			RF2_INACT_DESCRIPTION_REFSET=xmlConfig.getString("rf2.descriptionInactivationRefset");

			RF2RF1inactStatMap=new HashMap<String, String>();
			//			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.NotStatedReason"),xmlConfig.getString("rf1.inactivationStatus.NotStatedReason"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Limited"),xmlConfig.getString("rf1.inactivationStatus.Limited"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Duplicate"),xmlConfig.getString("rf1.inactivationStatus.Duplicate"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Pending_move"),xmlConfig.getString("rf1.inactivationStatus.Pending_move"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Ambiguous"),xmlConfig.getString("rf1.inactivationStatus.Ambiguous"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Moved_elsewhere"),xmlConfig.getString("rf1.inactivationStatus.Moved_elsewhere"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Concept_non-current"),xmlConfig.getString("rf1.inactivationStatus.Concept_non-current"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Outdated"),xmlConfig.getString("rf1.inactivationStatus.Outdated"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Inappropriate"),xmlConfig.getString("rf1.inactivationStatus.Inappropriate"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Erroneous"),xmlConfig.getString("rf1.inactivationStatus.Erroneous"));

			RF1RF2inactStatMap=new HashMap<String, String>();
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.NotStatedReason"),xmlConfig.getString("rf2.inactivationStatus.NotStatedReason"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Limited"),xmlConfig.getString("rf2.inactivationStatus.Limited"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Duplicate"),xmlConfig.getString("rf2.inactivationStatus.Duplicate"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Pending_move"),xmlConfig.getString("rf2.inactivationStatus.Pending_move"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Ambiguous"),xmlConfig.getString("rf2.inactivationStatus.Ambiguous"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Moved_elsewhere"),xmlConfig.getString("rf2.inactivationStatus.Moved_elsewhere"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Concept_non-current"),xmlConfig.getString("rf2.inactivationStatus.Concept_non-current"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Outdated"),xmlConfig.getString("rf2.inactivationStatus.Outdated"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Inappropriate"),xmlConfig.getString("rf2.inactivationStatus.Inappropriate"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Erroneous"),xmlConfig.getString("rf2.inactivationStatus.Erroneous"));


			RF2RF1AssociationMap=new HashMap<String, String>();
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Part_Of"),xmlConfig.getString("rf1.association.Part_Of"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.May_Be"),xmlConfig.getString("rf1.association.May_Be"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Was_A"),xmlConfig.getString("rf1.association.Was_A"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Same_As"),xmlConfig.getString("rf1.association.Same_As"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Replaced_By"),xmlConfig.getString("rf1.association.Replaced_By"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Moved_To"),xmlConfig.getString("rf1.association.Moved_To"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Moved_From"),xmlConfig.getString("rf1.association.Moved_From"));

			RF1RF2AssociationMap=new HashMap<String, String>();
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Part_Of"),xmlConfig.getString("rf2.association.Part_Of"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.May_Be"),xmlConfig.getString("rf2.association.May_Be"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Was_A"),xmlConfig.getString("rf2.association.Was_A"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Same_As"),xmlConfig.getString("rf2.association.Same_As"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Replaced_By"),xmlConfig.getString("rf2.association.Replaced_By"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Moved_To"),xmlConfig.getString("rf2.association.Moved_To"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Moved_From"),xmlConfig.getString("rf2.association.Moved_From"));

			RF1CauseInactiveConcept=new TreeSet<String>();


			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.ambiguous"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.duplicate"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.erroneous"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.limited"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.moved"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.outdated"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.reasonNotStated"));
			RF2References=new HashMap<String,String[]>();
			
			RF2References.put(xmlConfig.getString("rf2.references.Replaced_By.refset"),new String[]{xmlConfig.getString("rf2.references.Replaced_By.validSourceStatus"),xmlConfig.getString("rf2.references.Replaced_By.rf1Value")});
			RF2References.put(xmlConfig.getString("rf2.references.Alternative.refset"),new String[]{xmlConfig.getString("rf2.references.Alternative.validSourceStatus"),xmlConfig.getString("rf2.references.Alternative.rf1Value")});
			RF2References.put(xmlConfig.getString("rf2.references.Refers_To.refset"),new String[]{xmlConfig.getString("rf2.references.Refers_To.validSourceStatus"),xmlConfig.getString("rf2.references.Refers_To.rf1Value")});
			
			RF2_ISA_RELATIONSHIP=xmlConfig.getString("rf2.isarelationship");
			RF1_ISA_RELATIONSHIP=xmlConfig.getString("rf1.isarelationship");
			RF2DescriptionReferences=new HashSet<String>();
			RF2DescriptionReferences.add(xmlConfig.getString("rf2.references.Refers_To.refset"));

			metadataModelSCTID=xmlConfig.getString("rf2.metadataModelSCTID");
			namespaceCptSCTID=xmlConfig.getString("rf2.namespaceCptSCTID");
			linkageCptSCTID=xmlConfig.getString("rf2.linkageCptSCTID");
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}


	/**
	 * Gets the r f1_ is a_ relationship.
	 *
	 * @return the r f1_ is a_ relationship
	 */
	public String getRF1_ISA_RELATIONSHIP() {
		return RF1_ISA_RELATIONSHIP;
	}


	/**
	 * Gets the r f1 cause inactive concept.
	 *
	 * @return the r f1 cause inactive concept
	 */
	public TreeSet<String> getRF1CauseInactiveConcept() {
		return RF1CauseInactiveConcept;
	}


	/**
	 * Gets the r f1 r f2 association map.
	 *
	 * @return the r f1 r f2 association map
	 */
	public HashMap<String, String> getRF1RF2AssociationMap() {
		return RF1RF2AssociationMap;
	}


	/**
	 * Gets the r f2_ ic s_ nosignificant.
	 *
	 * @return the r f2_ ic s_ nosignificant
	 */
	public String getRF2_ICS_NOSIGNIFICANT() {
		return RF2_ICS_NOSIGNIFICANT;
	}


	/**
	 * Gets the r f1 r f2inact stat map.
	 *
	 * @return the r f1 r f2inact stat map
	 */
	public HashMap<String, String> getRF1RF2inactStatMap() {
		return RF1RF2inactStatMap;
	}


	/**
	 * Gets the r f2_ de f_ statu s_ defined.
	 *
	 * @return the r f2_ de f_ statu s_ defined
	 */
	public String getRF2_DEF_STATUS_DEFINED() {
		return RF2_DEF_STATUS_DEFINED;
	}


	/**
	 * Gets the r f1 r f2 refina map.
	 *
	 * @return the r f1 r f2 refina map
	 */
	public HashMap<String, String> getRF1RF2RefinaMap() {
		return RF1RF2RefinaMap;
	}


	/**
	 * Gets the r f2 modifier some.
	 *
	 * @return the r f2 modifier some
	 */
	public String getRF2ModifierSome() {
		return RF2ModifierSome;
	}


	/**
	 * Gets the r f1 r f2char type map.
	 *
	 * @return the r f1 r f2char type map
	 */
	public HashMap<String, String> getRF1RF2charTypeMap() {
		return RF1RF2charTypeMap;
	}


	/**
	 * Gets the r f2_ fsn.
	 *
	 * @return the r f2_ fsn
	 */
	public String getRF2_FSN() {
		return RF2_FSN;
	}


	/**
	 * Gets the r f2_ synonym.
	 *
	 * @return the r f2_ synonym
	 */
	public String getRF2_SYNONYM() {
		return RF2_SYNONYM;
	}


	/**
	 * Gets the r f2_ preferred.
	 *
	 * @return the r f2_ preferred
	 */
	public String getRF2_PREFERRED() {
		return RF2_PREFERRED;
	}


	/**
	 * Gets the r f2_ e n_ refset.
	 *
	 * @return the r f2_ e n_ refset
	 */
	public String getRF2_EN_REFSET() {
		return RF2_EN_REFSET;
	}


	/**
	 * Gets the r f2_ ic s_ significant.
	 *
	 * @return the r f2_ ic s_ significant
	 */
	public String getRF2_ICS_SIGNIFICANT() {
		return RF2_ICS_SIGNIFICANT;
	}


	/**
	 * Gets the r f2_ de f_ statu s_ primitive.
	 *
	 * @return the r f2_ de f_ statu s_ primitive
	 */
	public String getRF2_DEF_STATUS_PRIMITIVE() {
		return RF2_DEF_STATUS_PRIMITIVE;
	}


	/**
	 * Gets the r f2_ ct v3 i d_ refsetid.
	 *
	 * @return the r f2_ ct v3 i d_ refsetid
	 */
	public String getRF2_CTV3ID_REFSETID() {
		return RF2_CTV3ID_REFSETID;
	}


	/**
	 * Gets the r f2_ snomedi d_ refsetid.
	 *
	 * @return the r f2_ snomedi d_ refsetid
	 */
	public String getRF2_SNOMEDID_REFSETID() {
		return RF2_SNOMEDID_REFSETID;
	}


	/**
	 * Gets the r f1_ fsn.
	 *
	 * @return the r f1_ fsn
	 */
	public String getRF1_FSN() {
		return RF1_FSN;
	}


	/**
	 * Gets the r f1_ synonym.
	 *
	 * @return the r f1_ synonym
	 */
	public String getRF1_SYNONYM() {
		return RF1_SYNONYM;
	}


	/**
	 * Gets the r f1_ preferred.
	 *
	 * @return the r f1_ preferred
	 */
	public String getRF1_PREFERRED() {
		return RF1_PREFERRED;
	}


	/**
	 * Gets the r f1_ e n_ subset.
	 *
	 * @return the r f1_ e n_ subset
	 */
	public String getRF1_EN_SUBSET() {
		return RF1_EN_SUBSET;
	}


	/**
	 * Gets the r f1_ g b_ subset.
	 *
	 * @return the r f1_ g b_ subset
	 */
	public String getRF1_GB_SUBSET() {
		return RF1_GB_SUBSET;
	}


	/**
	 * Gets the r f1_ gblan g_ code.
	 *
	 * @return the r f1_ gblan g_ code
	 */
	public String getRF1_GBLANG_CODE() {
		return RF1_GBLANG_CODE;
	}


	/**
	 * Gets the r f1_ uslan g_ code.
	 *
	 * @return the r f1_ uslan g_ code
	 */
	public String getRF1_USLANG_CODE() {
		return RF1_USLANG_CODE;
	}


	/**
	 * Gets the r f1_ enlan g_ code.
	 *
	 * @return the r f1_ enlan g_ code
	 */
	public String getRF1_ENLANG_CODE() {
		return RF1_ENLANG_CODE;
	}


	/**
	 * Gets the r f1_ subse t_ defined.
	 *
	 * @return the r f1_ subse t_ defined
	 */
	public String getRF1_SUBSET_DEFINED() {
		return RF1_SUBSET_DEFINED;
	}


	/**
	 * Gets the r f2_ acceptable.
	 *
	 * @return the r f2_ acceptable
	 */
	public String getRF2_ACCEPTABLE() {
		return RF2_ACCEPTABLE;
	}


	/**
	 * Gets the r f2_ enu s_ refset.
	 *
	 * @return the r f2_ enu s_ refset
	 */
	public String getRF2_ENUS_REFSET() {
		return RF2_ENUS_REFSET;
	}


	/**
	 * Gets the r f2_ eng b_ refset.
	 *
	 * @return the r f2_ eng b_ refset
	 */
	public String getRF2_ENGB_REFSET() {
		return RF2_ENGB_REFSET;
	}


	/**
	 * Gets the r f2_ refinabilit y_ refsetid.
	 *
	 * @return the r f2_ refinabilit y_ refsetid
	 */
	public String getRF2_REFINABILITY_REFSETID() {
		return RF2_REFINABILITY_REFSETID;
	}


	/**
	 * Gets the r f2 r f1 refina map.
	 *
	 * @return the r f2 r f1 refina map
	 */
	public HashMap<String, String> getRF2RF1RefinaMap() {
		return RF2RF1RefinaMap;
	}


	/**
	 * Gets the r f2 r f1char type map.
	 *
	 * @return the r f2 r f1char type map
	 */
	public HashMap<String, String> getRF2RF1charTypeMap() {
		return RF2RF1charTypeMap;
	}


	/**
	 * Gets the r f2 r f1inact stat map.
	 *
	 * @return the rF2RF1inactStatMap
	 */
	public HashMap<String, String> getRF2RF1inactStatMap() {
		return RF2RF1inactStatMap;
	}


	/**
	 * Gets the r f2_ inac t_ concep t_ refset.
	 *
	 * @return the rF2_INACT_CONCEPT_REFSET
	 */
	public String getRF2_INACT_CONCEPT_REFSET() {
		return RF2_INACT_CONCEPT_REFSET;
	}


	/**
	 * Gets the r f2_ inac t_ descriptio n_ refset.
	 *
	 * @return the rF2_INACT_DESCRIPTION_REFSET
	 */
	public String getRF2_INACT_DESCRIPTION_REFSET() {
		return RF2_INACT_DESCRIPTION_REFSET;
	}


	/**
	 * Gets the r f2 r f1 association map.
	 *
	 * @return the r f2 r f1 association map
	 */
	public HashMap<String, String> getRF2RF1AssociationMap() {
		return RF2RF1AssociationMap;
	}


	/**
	 * Gets the r f2_ is a_ relationship.
	 *
	 * @return the rF2_ISA_RELATIONSHIP
	 */
	public String getRF2_ISA_RELATIONSHIP() {
		return RF2_ISA_RELATIONSHIP;
	}

	/**
	 * Gets the r f2 references.
	 *
	 * @return the r f2 references
	 */
	public HashMap<String, String[]> getRF2References() {
		return RF2References;
	}


	/**
	 * Gets the r f2_ c a_ refset.
	 *
	 * @return the r f2_ c a_ refset
	 */
	public String getRF2_CA_REFSET() {
		return RF2_CA_REFSET;
	}


	/**
	 * Gets the r f2_ e s_ refset.
	 *
	 * @return the r f2_ e s_ refset
	 */
	public String getRF2_ES_REFSET() {
		return RF2_ES_REFSET;
	}

	/**
	 * Gets the r f2 description references.
	 *
	 * @return the r f2 description references
	 */
	public HashSet<String> getRF2DescriptionReferences() {
		return RF2DescriptionReferences;
	}


	/**
	 * Gets the metadata model sctid.
	 *
	 * @return the metadata model sctid
	 */
	public String getMetadataModelSCTID() {
		return metadataModelSCTID;
	}


	/**
	 * Gets the namespace cpt sctid.
	 *
	 * @return the namespace cpt sctid
	 */
	public String getNamespaceCptSCTID() {
		return namespaceCptSCTID;
	}


	/**
	 * Gets the linkage cpt sctid.
	 *
	 * @return the linkage cpt sctid
	 */
	public String getLinkageCptSCTID() {
		return linkageCptSCTID;
	}
}
