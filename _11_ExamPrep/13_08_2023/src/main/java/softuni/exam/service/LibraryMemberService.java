package softuni.exam.service;

import softuni.exam.models.entity.LibraryMember;

import java.io.IOException;

// TODO: Implement all methods
public interface LibraryMemberService {

    boolean areImported();

    String readLibraryMembersFileContent() throws IOException;
	
	String importLibraryMembers() throws IOException;

    LibraryMember getLibraryMemberById(int id);
}
