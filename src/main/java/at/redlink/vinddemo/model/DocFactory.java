package at.redlink.vinddemo.model;

import com.rbmhtechnology.vind.api.Document;
import com.rbmhtechnology.vind.model.DocumentFactory;
import com.rbmhtechnology.vind.model.DocumentFactoryBuilder;
import com.rbmhtechnology.vind.model.FieldDescriptorBuilder;
import com.rbmhtechnology.vind.model.MultiValueFieldDescriptor;
import com.rbmhtechnology.vind.model.SingleValueFieldDescriptor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public class DocFactory {

    public static final SingleValueFieldDescriptor<String> TITLE_FIELD = new FieldDescriptorBuilder<String>()
            .setFullText(true)
            .buildTextField("title");

    public static final SingleValueFieldDescriptor<ZonedDateTime> DATE_FIELD = new FieldDescriptorBuilder<ZonedDateTime>()
            .buildDateField("date");

    public static final MultiValueFieldDescriptor<String> TAGS_FIELD = new FieldDescriptorBuilder<String>()
            .setFullText(true)
            .setFacet(true)
            .buildMultivaluedTextField("tags");

    public static final SingleValueFieldDescriptor<Double> RATING_FIELD = new FieldDescriptorBuilder<Double>()
            .buildNumericField("rating", Double.class);

    public static final DocumentFactory FACTORY = new DocumentFactoryBuilder("docs")
            .addField(
                    TITLE_FIELD,
                    DATE_FIELD,
                    TAGS_FIELD,
                    RATING_FIELD
            ).build();

    public static Document createDocument(String id, String title, ZonedDateTime date, Set<String> tags, double rating) {
        return FACTORY.createDoc(id)
                .setValue(TITLE_FIELD, title)
                .setValue(DATE_FIELD, date)
                .setValues(TAGS_FIELD, tags)
                .setValue(RATING_FIELD, rating);
    }

    public static Doc createDoc(Document document) {
        return new Doc()
                .setId(document.getId())
                .setTitle(document.getValue(TITLE_FIELD))
                .setDate(document.getValue(DATE_FIELD))
                .setTags((List) document.getValues().get(TAGS_FIELD.getName()))
                .setRating(document.getValue(RATING_FIELD));
    }

}
