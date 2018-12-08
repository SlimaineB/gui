/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CmRequestService } from 'app/entities/cm-request/cm-request.service';
import { ICmRequest, CmRequest } from 'app/shared/model/cm-request.model';

describe('Service Tests', () => {
    describe('CmRequest Service', () => {
        let injector: TestBed;
        let service: CmRequestService;
        let httpMock: HttpTestingController;
        let elemDefault: ICmRequest;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CmRequestService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CmRequest(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        startDateTime: currentDate.format(DATE_TIME_FORMAT),
                        endDateTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CmRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        startDateTime: currentDate.format(DATE_TIME_FORMAT),
                        endDateTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDateTime: currentDate,
                        endDateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CmRequest(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CmRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        requestUuid: 'BBBBBB',
                        serviceName: 'BBBBBB',
                        serviceEndpoint: 'BBBBBB',
                        instanceHostname: 'BBBBBB',
                        instancePort: 1,
                        requestBody: 'BBBBBB',
                        requestHeader: 'BBBBBB',
                        responseBody: 'BBBBBB',
                        responseHeader: 'BBBBBB',
                        returnHttpCode: 1,
                        technicalStatus: 'BBBBBB',
                        functionalStatus: 'BBBBBB',
                        startDateTime: currentDate.format(DATE_TIME_FORMAT),
                        endDateTime: currentDate.format(DATE_TIME_FORMAT),
                        requestDuration: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        startDateTime: currentDate,
                        endDateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CmRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        requestUuid: 'BBBBBB',
                        serviceName: 'BBBBBB',
                        serviceEndpoint: 'BBBBBB',
                        instanceHostname: 'BBBBBB',
                        instancePort: 1,
                        requestBody: 'BBBBBB',
                        requestHeader: 'BBBBBB',
                        responseBody: 'BBBBBB',
                        responseHeader: 'BBBBBB',
                        returnHttpCode: 1,
                        technicalStatus: 'BBBBBB',
                        functionalStatus: 'BBBBBB',
                        startDateTime: currentDate.format(DATE_TIME_FORMAT),
                        endDateTime: currentDate.format(DATE_TIME_FORMAT),
                        requestDuration: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDateTime: currentDate,
                        endDateTime: currentDate
                    },
                    returnedFromService
                );
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

            it('should delete a CmRequest', async () => {
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
