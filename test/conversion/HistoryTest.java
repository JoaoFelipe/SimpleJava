/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conversion;

import history.History;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joao
 */
public class HistoryTest {
    
    public HistoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void withoutChangesPositionsShouldBeTheSame() {
        History history = new History();
        assertEquals(1, history.getOriginal(1));
        assertEquals(10, history.getOriginal(10));
        assertEquals(50, history.getOriginal(50));
    }
    
    @Test
    public void ifAddedBeforePosition_OriginalPositionShouldBeSmaller() {
        History history = new History();
        history.add(5, 10);
        assertEquals(5, history.getOriginal(15));
        assertEquals(90, history.getOriginal(100));
        assertEquals(40, history.getOriginal(50));
    }

    @Test
    public void ifAddedAfterPosition_OriginalPositionShouldBeTheSame() {
        History history = new History();
        history.add(5, 10);
        assertEquals(1, history.getOriginal(1));
        assertEquals(4, history.getOriginal(4));
    }
    
    @Test
    public void twoAddOperations() {
        History history = new History();
        history.add(5, 10);
        history.add(1, 10);
        assertEquals(5, history.getOriginal(25));
        assertEquals(4, history.getOriginal(14));
    }
    
    @Test
    public void twoAddOperationsReverseOrder() {
        History history = new History();
        history.add(1, 10);
        history.add(5, 10);
        assertEquals(1, history.getOriginal(21));
    }
    
    @Test
    public void nonExistentInOriginalShouldReturnAddedPosition() {
        History history = new History();
        history.add(1, 10);
        assertEquals(1, history.getOriginal(10));
        assertEquals(1, history.getOriginal(9));
        assertEquals(1, history.getOriginal(8));
//        assertEquals(4, history.getOriginal(14));
    }
    
    @Test
    public void ifRemovedBeforePosition_OriginalPositionShouldBeBigger() {
        History history = new History();
        history.remove(5, 10);
        assertEquals(15, history.getOriginal(5));
        assertEquals(110, history.getOriginal(100));
        assertEquals(60, history.getOriginal(50));
    }
    
    @Test
    public void ifRemovedAfterPosition_OriginalPositionShouldBeTheSame() {
        History history = new History();
        history.remove(5, 10);
        assertEquals(4, history.getOriginal(4));
        assertEquals(3, history.getOriginal(3));
        assertEquals(1, history.getOriginal(1));
    }

    @Test
    public void twoRemoveOperations() {
        History history = new History();
        history.remove(1, 10);
        history.remove(5, 10);
        assertEquals(25, history.getOriginal(5));
        assertEquals(14, history.getOriginal(4));
    }
    
    @Test
    public void twoRemoveOperationsReverseOrder() {
        History history = new History();
        history.remove(5, 10);
        history.remove(1, 10);
        assertEquals(25, history.getOriginal(5));
        assertEquals(24, history.getOriginal(4));
    }
    
    @Test
    public void ifMovementOccurredAfterPosition_OriginalPositionShouldBeTheSame() {
        History history = new History();
        history.move(5, 15, 10);
        assertEquals(4, history.getOriginal(4));
        assertEquals(1, history.getOriginal(1));
    }
    
    @Test
    public void ifNewBlockContainsPosition_OriginalPositionShouldBeOriginalLessNew() {
        History history = new History();
        history.move(5, 15, 10);
        assertEquals(5, history.getOriginal(15));
        assertEquals(6, history.getOriginal(16));
    }

    @Test
    public void ifNewBlockContainsPosition_OriginalPositionShouldBeOriginalLessNew_Reverse() {
        History history = new History();
        history.move(15, 5, 10);
        assertEquals(15, history.getOriginal(5));
        assertEquals(16, history.getOriginal(6));
    }

    @Test
    public void ifOriginalBlockContainsPosition_OriginalPositionShouldBeNewLessOriginal() {
        History history = new History();
        history.move(5, 15, 10);
        assertEquals(15, history.getOriginal(5));
        assertEquals(16, history.getOriginal(6));
    }
    
    @Test
    public void ifOriginalBlockContainsPosition_OriginalPositionShouldBeNewLessOriginal_Reverse() {
        History history = new History();
        history.move(15, 5, 10);
        assertEquals(5, history.getOriginal(15));
        assertEquals(6, history.getOriginal(16));
    }
    
    @Test
    public void ifPositionBetweenBlocksInForwardMovement_OriginalPositionShouldBeGreater() {
        History history = new History();
        history.move(5, 10, 2);
        assertEquals(7, history.getOriginal(5));
        assertEquals(8, history.getOriginal(6));
    }
    
    @Test
    public void ifPositionBetweenBlocksInBackwardMovement_OriginalPositionShouldBeLower() {
        History history = new History();
        history.move(10, 5, 2);
        assertEquals(5, history.getOriginal(7));
        assertEquals(6, history.getOriginal(8));
    }
    
    @Test
    public void ifAddedBeforePosition_NewPositionShouldBeBigger() {
        History history = new History();
        history.add(5, 10);
        assertEquals(25, history.getNew(15));
        assertEquals(110, history.getNew(100));
        assertEquals(60, history.getNew(50));
    }
    
    @Test
    public void ifAddedAfterPosition_NewPositionShouldBeTheSame() {
        History history = new History();
        history.add(5, 10);
        assertEquals(1, history.getNew(1));
        assertEquals(4, history.getNew(4));
    }
    
    @Test
    public void ifRemovedBeforePosition_NewPositionShouldBeSmaller() {
        History history = new History();
        history.remove(5, 10);
        assertEquals(5, history.getNew(15));
        assertEquals(100, history.getNew(110));
        assertEquals(50, history.getNew(60));
    }
    
    @Test
    public void ifRemovedAfterPosition_NewPositionShouldBeTheSame() {
        History history = new History();
        history.remove(5, 10);
        assertEquals(4, history.getNew(4));
        assertEquals(3, history.getNew(3));
        assertEquals(1, history.getNew(1));
    }
    
    @Test
    public void nonExistentInNewShouldReturnRemovedPosition() {
        History history = new History();
        history.remove(1, 10);
        assertEquals(1, history.getNew(10));
        assertEquals(1, history.getNew(9));
        assertEquals(1, history.getNew(8));
//        assertEquals(4, history.getOriginal(14));
    }
    
    @Test
    public void ifOriginalBlockContainsPosition_NewPositionShouldBeNewLessOriginal_Reverse() {
        History history = new History();
        history.move(15, 5, 10);
        assertEquals(5, history.getNew(15));
        assertEquals(6, history.getNew(16));
    }
    
    @Test
    public void ifMovementOccurredAfterPosition_NewPositionShouldBeTheSame() {
        History history = new History();
        history.move(5, 15, 10);
        assertEquals(4, history.getNew(4));
        assertEquals(1, history.getNew(1));
    }
    
    @Test
    public void ifOriginalContainsPosition_NewPositionShouldBeNewLessOriginal() {
        History history = new History();
        history.move(5, 15, 10);
        assertEquals(15, history.getNew(5));
        assertEquals(16, history.getNew(6));
    }
    
    @Test
    public void ifNewBlockContainsPosition_NewPositionShouldBeOriginalLessNew() {
        History history = new History();
        history.move(5, 15, 10);
        assertEquals(5, history.getNew(15));
        assertEquals(6, history.getNew(16));
    }
    
    @Test
    public void ifNewBlockContainsPosition_NewPositionShouldBeOriginalLessNew_Reverse() {
        History history = new History();
        history.move(15, 5, 10);
        assertEquals(15, history.getNew(5));
        assertEquals(16, history.getNew(6));
    }
    
    @Test
    public void ifPositionBetweenBlocksInForwardMovement_NewPositionShouldBeLower() {
        History history = new History();
        history.move(5, 10, 2);
        assertEquals(5, history.getNew(7));
        assertEquals(6, history.getNew(8));
    }
    
    @Test
    public void ifPositionBetweenBlocksInBackwardMovement_NewPositionShouldBeGreater() {
        History history = new History();
        history.move(10, 5, 2);
        assertEquals(7, history.getNew(5));
        assertEquals(8, history.getNew(6));
    }
}
