package project.Test.chequerUCMTest;

import static org.junit.Assert.*;

import org.junit.Test;

import project.chequerUCM.Chequer;

public class chequerUCMTest {

	@Test
	public void testIsValid() {
		Chequer chequer1 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba1.jucm");
		Chequer chequer2 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba2.jucm");
		Chequer chequer3 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba3.jucm");
		Chequer chequer4 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba4.jucm");
		Chequer chequer5 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba5.jucm");
		Chequer chequer6 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba6.jucm");
		Chequer chequer7 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba7.jucm");
		Chequer chequer8 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba8.jucm");
		Chequer chequer9 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba9.jucm");
		Chequer chequer10 = new Chequer("C:/Users/Usuario-Pc/git/project/Project/src/project/Test/chequerUCMTest/UCM/prueba10.jucm");
		
		assertEquals(2, chequer1.isValid());
		assertEquals(2, chequer2.isValid());
		assertEquals(3, chequer3.isValid());
		assertEquals(3, chequer4.isValid());
		assertEquals(3, chequer5.isValid());
		assertEquals(3, chequer6.isValid());
		assertEquals(3, chequer7.isValid());
		assertEquals(0, chequer8.isValid());
		assertEquals(1, chequer9.isValid());
		assertEquals(1, chequer10.isValid());
	}

}
