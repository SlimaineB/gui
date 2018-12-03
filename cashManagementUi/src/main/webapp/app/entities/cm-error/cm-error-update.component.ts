import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICmError } from 'app/shared/model/cm-error.model';
import { CmErrorService } from './cm-error.service';
import { ICmRequest } from 'app/shared/model/cm-request.model';
import { CmRequestService } from 'app/entities/cm-request';

@Component({
    selector: 'jhi-cm-error-update',
    templateUrl: './cm-error-update.component.html'
})
export class CmErrorUpdateComponent implements OnInit {
    cmError: ICmError;
    isSaving: boolean;

    cmrequests: ICmRequest[];
    errornDateTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cmErrorService: CmErrorService,
        private cmRequestService: CmRequestService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cmError }) => {
            this.cmError = cmError;
            this.errornDateTime = this.cmError.errornDateTime != null ? this.cmError.errornDateTime.format(DATE_TIME_FORMAT) : null;
        });
        this.cmRequestService.query().subscribe(
            (res: HttpResponse<ICmRequest[]>) => {
                this.cmrequests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cmError.errornDateTime = this.errornDateTime != null ? moment(this.errornDateTime, DATE_TIME_FORMAT) : null;
        if (this.cmError.id !== undefined) {
            this.subscribeToSaveResponse(this.cmErrorService.update(this.cmError));
        } else {
            this.subscribeToSaveResponse(this.cmErrorService.create(this.cmError));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICmError>>) {
        result.subscribe((res: HttpResponse<ICmError>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCmRequestById(index: number, item: ICmRequest) {
        return item.id;
    }
}
