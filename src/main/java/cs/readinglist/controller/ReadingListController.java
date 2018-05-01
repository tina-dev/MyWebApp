package cs.readinglist.controller;

import cs.readinglist.entity.Book;
import cs.readinglist.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ReadingListController {
    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String getIndex()
    {
        return "index";
    }

    @RequestMapping(value="/readinglist", method= RequestMethod.GET)
    public String getReadingList(Model model)
    {
        List<Book> readingList = readingListRepository.findAll();

        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readinglist";
    }

    @RequestMapping(value="/readinglist", method=RequestMethod.POST)
    public String addToReadingList(Book book)
    {
    	try
    	{
    		readingListRepository.save(book);
    	}
    	catch (Exception e)
    	{
    		System.out.println("Cannot save this book, error:" + e.getMessage());
    	}
    	
    	return "redirect:/readinglist";
    }
    
    @RequestMapping(value="/readinglist/{isbn}", method= RequestMethod.GET)
    public String getBookVeiw(Model model, @PathVariable String isbn)
    {
    	List<Book> bookList = readingListRepository.findByIsbn(isbn);
    	
    	model.addAttribute("book", bookList.get(0));
    	
        return "bookview";
    }
    
    @RequestMapping(value="/readinglist/edit/{isbn}", method= RequestMethod.GET)
    public String getBookEdit(Model model, @PathVariable String isbn)
    {
    	List<Book> bookList = readingListRepository.findByIsbn(isbn);
    	
    	model.addAttribute("book", bookList.get(0));

    	return "bookedit";
    }
    
    @RequestMapping(value="/readinglist/edit/{isbn}", method= RequestMethod.POST)
    public String editBook(Model model, Book book)
    {
    	try
    	{
    		readingListRepository.save(book);
    	}
    	catch (Exception e)
    	{
    		System.out.println("Cannot edit/update this book, error:" + e.getMessage());
    	}
    	
    	return "redirect:/readinglist";
    }
    
    @RequestMapping(value="/readinglist/delete/{isbn}", method= RequestMethod.POST)
    public String deleteBook(Model model, @PathVariable String isbn)
    {
    	readingListRepository.deleteByIsbn(isbn);

        return "redirect:/readinglist";
    }
}