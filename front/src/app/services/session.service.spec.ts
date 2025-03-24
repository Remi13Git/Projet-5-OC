import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { BehaviorSubject } from 'rxjs';

describe('SessionService (Integration)', () => {
  let service: SessionService;

  const mockSessionInformation: SessionInformation = {
    token: 'mockToken123',
    type: 'admin',
    username: 'mockUser',
    firstName: 'John',
    lastName: 'Doe',
    admin: true,
    id: 1
  };

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in the user and emit the correct values', (done) => {
    service.$isLogged().subscribe((isLogged) => {
      if (isLogged) {
        expect(isLogged).toBe(true);
        expect(service.sessionInformation).toEqual(mockSessionInformation);
        done();
      }
    });
    service.logIn(mockSessionInformation); // Trigger login
  });

  it('should log out the user and emit the correct values', (done) => {
    service.logIn(mockSessionInformation); // Log in first
    service.$isLogged().subscribe((isLogged) => {
      if (!isLogged) {
        expect(isLogged).toBe(false);
        expect(service.sessionInformation).toBeUndefined();
        done();
      }
    });
    service.logOut(); // Trigger logout
  });

  it('should emit isLogged as true after log in and false after log out', (done) => {
    // Log in first
    service.$isLogged().subscribe((isLogged) => {
      if (isLogged) {
        expect(isLogged).toBe(true);
        service.logOut(); // Trigger logout
      }
    });
    service.logIn(mockSessionInformation); // Trigger login

    // Test after log out
    service.$isLogged().subscribe((isLogged) => {
      if (!isLogged) {
        expect(isLogged).toBe(false);
        done();
      }
    });
  });
});
