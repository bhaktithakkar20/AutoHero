package resources.utilities;

import java.util.List;

public class Ordering {

	/**
	 * This method contains logic to check if the list contains values less than the
	 * deciding value.
	 * 
	 * @param decidingValue    : The value used for comparisons.
	 * @param listtoBeVerified : The list whose contents need to be checked against
	 *                         decidingValue.
	 * @return Boolean: True/False indicating whether list contains values less than
	 *         deciding value.
	 */
	public static Boolean isListHavingSmallerValue(Integer decidingValue, List<Integer> listToBeVerified) {

		for (int i = 0; i < listToBeVerified.size(); i++) {
			if (listToBeVerified.get(i) < decidingValue) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method contains logic to identify the number of pages that need to be
	 * traversed when total records and number of records per page is provided.
	 * 
	 * @param totalRecordsAavailable : The total number of records available.
	 * @param numberRecordsPerPage   : Number of records per page.
	 * @return Integer: Number of pages that needs to be iterated.
	 */
	public static Integer getNumberOfPagesToBeTraversed(Integer totalRecordsAavailable, Integer numberRecordsPerPage) {
		Integer totalPages = 0;
		if (totalRecordsAavailable % numberRecordsPerPage == 0) {
			totalPages = totalRecordsAavailable / numberRecordsPerPage;
		} else {
			totalPages = (totalRecordsAavailable / numberRecordsPerPage) + 1;
		}

		return totalPages;
	}

	/**
	 * This method contains logic to check whether the contents of list are sorted
	 * in descending order.
	 * 
	 * @param listToBeVerified : The list that needs to be verified.
	 * @return Boolean: True/False indicating if list is sorted correctly or not.
	 */
	public static boolean isListSortedDescending(List<Double> listToBeVerified) {
		for (int i = 1; i < listToBeVerified.size(); i++) {
			if (listToBeVerified.get(i - 1) < listToBeVerified.get(i)) {
				System.out.println("i=" + i);
				System.out.println("(i-1)=" + listToBeVerified.get(i - 1));
				System.out.println("(i)=" + listToBeVerified.get(i));
				return false;
			}
		}
		return true;
	}
}
