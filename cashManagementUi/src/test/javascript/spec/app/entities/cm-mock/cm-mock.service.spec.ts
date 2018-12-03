/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CmMockService } from 'app/entities/cm-mock/cm-mock.service';
import { ICmMock, CmMock } from 'app/shared/model/cm-mock.model';

describe('Service Tests', () => {
    describe('CmMock Service', () => {
        let injector: TestBed;
        let service: CmMockService;
        let httpMock: HttpTestingController;
        let elemDefault: ICmMock;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CmMockService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new CmMock(0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CmMock', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new CmMock(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CmMock', async () => {
                const returnedFromService = Object.assign(
                    {
                        mockId: 1,
                        mockServiceName: 1,
                        mockSearchKey: 'BBBBBB',
                        mockSearchValue: 'BBBBBB',
                        mockedBody: 'BBBBBB',
                        mockedHttpCode: 'BBBBBB',
                        mockedTime: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CmMock', async () => {
                const returnedFromService = Object.assign(
                    {
                        mockId: 1,
                        mockServiceName: 1,
                        mockSearchKey: 'BBBBBB',
                        mockSearchValue: 'BBBBBB',
                        mockedBody: 'BBBBBB',
                        mockedHttpCode: 'BBBBBB',
                        mockedTime: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a CmMock', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
