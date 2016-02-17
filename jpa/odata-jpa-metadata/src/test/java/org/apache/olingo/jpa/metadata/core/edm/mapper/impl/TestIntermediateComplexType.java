package org.apache.olingo.jpa.metadata.core.edm.mapper.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.persistence.metamodel.EmbeddableType;

import org.apache.olingo.jpa.metadata.api.JPAEdmMetadataPostProcessor;
import org.apache.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAModelException;
import org.apache.olingo.jpa.metadata.core.edm.mapper.extention.IntermediateNavigationPropertyAccess;
import org.apache.olingo.jpa.metadata.core.edm.mapper.extention.IntermediatePropertyAccess;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestIntermediateComplexType extends TestMappingRoot {
  private Set<EmbeddableType<?>> etList;
  private IntermediateSchema schema;

  @Before
  public void setup() throws ODataJPAModelException {
    IntermediateModelElement.SetPostProcessor(new DefaultEdmPostProcessor());
    etList = emf.getMetamodel().getEmbeddables();
    schema = new IntermediateSchema(new JPAEdmNameBuilder(PUNIT_NAME), emf.getMetamodel());

  }

  @Test
  public void checkComplexTypeCanBeCreated() throws ODataJPAModelException {

    new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType("CommunicationData"), schema);
  }

  private EmbeddableType<?> getEmbeddedableType(String typeName) {
    for (EmbeddableType<?> embeddableType : etList) {
      if (embeddableType.getJavaType().getSimpleName().equals(typeName)) {
        return embeddableType;
      }
    }
    return null;
  }

  @Test
  public void checkGetAllProperties() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "CommunicationData"), schema);
    assertEquals("Wrong number of entities", 4, ct.getEdmItem().getProperties().size());
  }

  @Test
  public void checkGetPropertyByNameNotNull() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "CommunicationData"), schema);
    assertNotNull(ct.getEdmItem().getProperty("LandlinePhoneNumber"));
  }

  @Test
  public void checkGetPropertyByNameCorrectEntity() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "CommunicationData"), schema);
    assertEquals("LandlinePhoneNumber", ct.getEdmItem().getProperty("LandlinePhoneNumber").getName());
  }

  @Test
  public void checkGetPropertyIsNullable() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    // TODO nullable not jet in $metadata: problem of Olingo?
    assertTrue(ct.getEdmItem().getProperty("POBox").isNullable());
  }

  @Test
  public void checkGetAllNaviProperties() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    assertEquals("Wrong number of entities", 1, ct.getEdmItem().getNavigationProperties().size());
  }

  @Test
  public void checkGetNaviPropertyByNameNotNull() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    assertNotNull(ct.getEdmItem().getNavigationProperty("AdministrativeDivision").getName());
  }

  @Test
  public void checkGetNaviPropertyByNameRightEntity() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    assertEquals("AdministrativeDivision", ct.getEdmItem().getNavigationProperty("AdministrativeDivision").getName());
  }

  @Test
  public void checkGetPropertiesSkipIgnored() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "CommunicationData"), schema);
    assertEquals("Wrong number of entities", 3, ct.getEdmItem().getProperties().size());
  }

  @Test
  public void checkGetDescriptionPropertyManyToOne() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    assertNotNull(ct.getEdmItem().getProperty("CountryName"));
  }

  @Test
  public void checkGetDescriptionPropertyManyToMany() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    assertNotNull(ct.getEdmItem().getProperty("RegionName"));
  }

  @Test
  public void checkDescriptionPropertyType() throws ODataJPAModelException {
    PostProcessorSetIgnore pPDouble = new PostProcessorSetIgnore();
    IntermediateModelElement.SetPostProcessor(pPDouble);

    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    ct.getEdmItem();
    assertTrue(ct.getProperty("countryName") instanceof IntermediateDescriptionProperty);
  }

  @Test
  public void checkGetPropertyOfNestedComplexType() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "AdministrativeInformation"), schema);
    assertNotNull(ct.getPath("Created/By"));
  }

  @Test
  public void checkGetPropertyDBName() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "PostalAddressData"), schema);
    assertEquals("\"Address.PostOfficeBox\"", ct.getPath("POBox").getDBFieldName());
  }

  @Test
  public void checkGetPropertyDBNameOfNestedComplexType() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "AdministrativeInformation"), schema);
    assertEquals("\"CreatedBy\"", ct.getPath("Created/By").getDBFieldName());
  }

  @Test
  public void checkGetPropertyWithComplexType() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "AdministrativeInformation"), schema);
    assertNotNull(ct.getEdmItem().getProperty("Created"));
  }

  @Test
  public void checkGetPropertiesWithSameComplexTypeNotEqual() throws ODataJPAModelException {
    IntermediateComplexType ct = new IntermediateComplexType(new JPAEdmNameBuilder(PUNIT_NAME), getEmbeddedableType(
        "AdministrativeInformation"), schema);
    assertNotEquals(ct.getEdmItem().getProperty("Created"), ct.getEdmItem().getProperty("Updated"));
    assertNotEquals(ct.getProperty("created"), ct.getProperty("updated"));
  }

  @Ignore
  @Test
  public void checkGetPropertyWithEnumerationType() {

  }

  private class PostProcessorSetIgnore extends JPAEdmMetadataPostProcessor {

    @Override
    public void processProperty(IntermediatePropertyAccess property, String jpaManagedTypeClassName) {
      if (jpaManagedTypeClassName.equals(
          Comm_CANONICAL_NAME)) {
        if (property.getInternalName().equals("landlinePhoneNumber")) {
          property.setIgnore(true);
        }
      }
    }

    @Override
    public void processNavigationProperty(IntermediateNavigationPropertyAccess property,
        String jpaManagedTypeClassName) {
      if (jpaManagedTypeClassName.equals(Addr_CANONICAL_NAME)) {
        if (property.getInternalName().equals("countryName")) {
          property.setIgnore(false);
        }
      }
    }
  }

}
