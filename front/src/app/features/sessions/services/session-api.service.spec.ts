import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  const mockSession: Session = {
    id: 1,
    name: 'Test Session',
    date: new Date('2025-03-23'),
    teacher_id: 1 ,
    users: [1, 2, 3],
    description: 'Test session description',
  };

  const mockSessions: Session[] = [
    { id: 1, name: 'Test Session 1', date: new Date('2025-03-23'), teacher_id: 1 , users: [1, 2, 3], description: 'Description 1' },
    { id: 2, name: 'Test Session 2', date: new Date('2025-03-23'), teacher_id: 1 , users: [1, 2, 3], description: 'Description 2' }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Utilisation de HttpClientTestingModule pour intercepter les requêtes HTTP
      providers: [SessionApiService],
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController); // Pour contrôler les requêtes HTTP dans les tests
  });

  afterEach(() => {
    // Vérifie qu'il n'y a pas de requêtes HTTP en attente après chaque test
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return all sessions', () => {
    service.all().subscribe((sessions) => {
      expect(sessions.length).toBe(2);
      expect(sessions).toEqual(mockSessions);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(mockSessions); // Simulation de la réponse HTTP
  });

  it('should return session details', () => {
    service.detail('1').subscribe((session) => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession); // Simulation de la réponse HTTP
  });

  it('should delete a session', () => {
    service.delete('1').subscribe((response) => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null); // Simulation de la réponse HTTP (aucun contenu)
  });

  it('should create a session', () => {
    service.create(mockSession).subscribe((session) => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockSession); // Vérifie que le corps de la requête correspond aux données
    req.flush(mockSession); // Simulation de la réponse HTTP
  });

  it('should update a session', () => {
    service.update('1', mockSession).subscribe((session) => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockSession); // Vérifie que le corps de la requête correspond aux données
    req.flush(mockSession); // Simulation de la réponse HTTP
  });

  it('should participate in a session', () => {
    service.participate('1', '123').subscribe(() => {
      expect(true).toBeTruthy();
    });

    const req = httpMock.expectOne('api/session/1/participate/123');
    expect(req.request.method).toBe('POST');
    req.flush(null); // Simulation de la réponse HTTP
  });

  it('should unparticipate from a session', () => {
    service.unParticipate('1', '123').subscribe(() => {
      expect(true).toBeTruthy();
    });

    const req = httpMock.expectOne('api/session/1/participate/123');
    expect(req.request.method).toBe('DELETE');
    req.flush(null); // Simulation de la réponse HTTP
  });
});
