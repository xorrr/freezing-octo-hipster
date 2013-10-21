package earth.xor.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import earth.xor.EmbedMongo;
import earth.xor.EmbedMongoProperties;
import earth.xor.ExampleLinks;

public class TestLinksDatastore {
    private MongoClient mongoClient;
    private LinksDatastore linksData;

    @BeforeClass
    public static void setUpEmbeddedMongo() throws UnknownHostException,
            IOException {
        EmbedMongo.getInstance();
    }

    @Before
    public void setUpTests() throws UnknownHostException {
        this.mongoClient = new MongoClient("localhost",
                EmbedMongoProperties.PORT);

        this.linksData = new LinksDatastore(mongoClient);
    }

    @Test
    public void testAddingAndGettingAllLinks() {
        linksData.addLink(ExampleLinks.testLink1);
        linksData.addLink(ExampleLinks.testLink2);
        linksData.addLink(ExampleLinks.testLink3);

        DBCursor linksCursor = linksData.getLinks();

        List<Link> linkList = createLinksArrayFromCursor(linksCursor);

        assertEquals("bar", linkList.get(1).getTitle());
        assertEquals("user3", linkList.get(2).getUser());

        assertNotNull(linkList.get(1).getTimeStamp());
    }

    @Test
    public void testGettingAUrlById() {
        linksData.addLink(ExampleLinks.testLink1);

        DBCollection col = mongoClient.getDB(LinksProp.DATABASE_NAME)
                .getCollection(LinksProp.LINKS_NAME);

        DBObject savedUrl = col.findOne();

        DBObject obj = linksData.getLinkById(savedUrl.get(LinksProp.ID)
                .toString());

        assertEquals("foo", obj.get(LinksProp.TITLE).toString());
        assertEquals(savedUrl.get(LinksProp.ID), obj.get(LinksProp.ID));
    }

    private List<Link> createLinksArrayFromCursor(DBCursor linksCurs) {
        List<Link> linkList = new ArrayList<Link>();
        iterateCursorToAddLinksToList(linksCurs, linkList);
        return linkList;
    }

    private void iterateCursorToAddLinksToList(DBCursor linksCurs,
            List<Link> linkList) {
        while (linksCurs.hasNext()) {
            DBObject dbo = linksCurs.next();
            linkList.add(dbObjectToLink(dbo));
        }
    }

    private Link dbObjectToLink(DBObject dbo) {
        return new Link(dbo.get(LinksProp.URL).toString(), dbo.get(
                LinksProp.TITLE).toString(),
                dbo.get(LinksProp.USER).toString(), dbo
                        .get(LinksProp.TIMESTAMP).toString());
    }

    @After
    public void dropLinksCollection() {
        mongoClient.getDB(LinksProp.DATABASE_NAME)
                .getCollection(LinksProp.LINKS_NAME).drop();
    }
}
