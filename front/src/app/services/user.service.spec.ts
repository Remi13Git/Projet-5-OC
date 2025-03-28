import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../interfaces/user.interface';

const mockUser: User = {
  id: 1,
  firstName: 'John',
  lastName: 'Doe',
  email: 'test@example.com',
  admin: false,
  password: 'password123',
  createdAt: new Date('2022-01-01T00:00:00Z')
};

describe('UserService', () => {
  let service: UserService;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [UserService]
    });

    service = TestBed.inject(UserService);
    httpClient = TestBed.inject(HttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getById and return the correct user', () => {
    const spyGet = jest.spyOn(httpClient, 'get').mockReturnValue(of(mockUser));

    const id = '1';
    service.getById(id).subscribe((user) => {
      expect(user).toEqual(mockUser);
    });

    expect(spyGet).toHaveBeenCalledWith('api/user/1');
  });

  it('should call delete and return null', () => {
    const spyDelete = jest.spyOn(httpClient, 'delete').mockReturnValue(of(null));

    const id = '1';
    service.delete(id).subscribe((response) => {
      expect(response).toBeNull();
    });

    expect(spyDelete).toHaveBeenCalledWith('api/user/1');
  });
});
