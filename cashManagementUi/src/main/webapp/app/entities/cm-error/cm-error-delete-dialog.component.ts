import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICmError } from 'app/shared/model/cm-error.model';
import { CmErrorService } from './cm-error.service';

@Component({
    selector: 'jhi-cm-error-delete-dialog',
    templateUrl: './cm-error-delete-dialog.component.html'
})
export class CmErrorDeleteDialogComponent {
    cmError: ICmError;

    constructor(private cmErrorService: CmErrorService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cmErrorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cmErrorListModification',
                content: 'Deleted an cmError'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cm-error-delete-popup',
    template: ''
})
export class CmErrorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmError }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CmErrorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cmError = cmError;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
