import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICmMock } from 'app/shared/model/cm-mock.model';
import { CmMockService } from './cm-mock.service';

@Component({
    selector: 'jhi-cm-mock-update',
    templateUrl: './cm-mock-update.component.html'
})
export class CmMockUpdateComponent implements OnInit {
    cmMock: ICmMock;
    isSaving: boolean;

    constructor(private cmMockService: CmMockService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cmMock }) => {
            this.cmMock = cmMock;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cmMock.id !== undefined) {
            this.subscribeToSaveResponse(this.cmMockService.update(this.cmMock));
        } else {
            this.subscribeToSaveResponse(this.cmMockService.create(this.cmMock));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICmMock>>) {
        result.subscribe((res: HttpResponse<ICmMock>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
