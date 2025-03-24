import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  // Mock des données
  const mockSessionInformation: SessionInformation = {
    token: 'mockToken123',
    type: 'admin',
    username: 'mockUser',
    firstName: 'John',
    lastName: 'Doe',
    admin: true,
    id: 1
  };

  // Mock du LoginRequest
  const mockLoginRequest: LoginRequest = {
    email: 'mockuser@example.com',
    password: 'mockPassword'
  };

  // Mock du RegisterRequest
  const mockRegisterRequest: RegisterRequest = {
    email: 'mockuser@example.com',
    firstName: 'John',
    lastName: 'Doe',
    password: 'mockPassword'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Importation du module de test HTTP
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);  // Injection du contrôleur de test HTTP
  });

  afterEach(() => {
    httpMock.verify();  // Vérification qu'il n'y a pas de requêtes HTTP en attente
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a POST request to register and return a success response', () => {
    // Effectuer l'appel au service
    service.register(mockRegisterRequest).subscribe((response) => {
      expect(response).toBeUndefined(); 
    });

    // Vérifier que la requête POST a bien été envoyée avec les bonnes données
    const req = httpMock.expectOne('api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockRegisterRequest);
    req.flush(null);  // Simuler une réponse vide
  });

  it('should send a POST request to login and return session information', () => {
    // Effectuer l'appel au service pour la connexion
    service.login(mockLoginRequest).subscribe((response) => {
      expect(response).toEqual(mockSessionInformation);  // Vérifier que les informations de session sont retournées
    });

    // Vérifier que la requête POST a bien été envoyée
    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockLoginRequest);
    req.flush(mockSessionInformation);  // Simuler la réponse avec les informations de session
  });
});
