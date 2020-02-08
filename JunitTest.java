package fmi.informatics.Junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JunitTest {

	@Test
	public void JunitTest() {
		Junit junit = new Junit();
		int result = junit.ab(100, 400);
		assertEquals(500, result);
	}

}
